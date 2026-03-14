import { Router, Request, Response } from "express";
import { v4 as uuidv4 } from "uuid";
import {
  InitiateTransferRequest,
  TransferResponse,
  ErrorResponse,
  TransferMode,
} from "../types.js";
import { ledger } from "../ledger.js";
import { logger } from "../logger.js";
import {
  validateIFSC,
  validateAccountNumber,
  shouldSucceed,
  initialStatus,
  settlementTime,
} from "../simulator.js";

export const transferRouter = Router();

/**
 * POST /api/bank/transfer
 * Wallet → Bank account (NEFT / IMPS / RTGS)
 */
transferRouter.post("/", (req: Request, res: Response) => {
  const body = req.body as InitiateTransferRequest;
  const timestamp = new Date().toISOString();

  // --- Validation ---
  if (!body.walletId || !body.amount || !body.bankAccountNumber || !body.ifscCode) {
    const err: ErrorResponse = {
      success: false,
      error: "Missing required fields: walletId, amount, bankAccountNumber, ifscCode",
      code: "INVALID_REQUEST",
      timestamp,
    };
    return res.status(400).json(err);
  }

  if (typeof body.amount !== "number" || body.amount <= 0) {
    const err: ErrorResponse = {
      success: false,
      error: "Amount must be a positive number",
      code: "INVALID_AMOUNT",
      timestamp,
    };
    return res.status(400).json(err);
  }

  if (!validateAccountNumber(body.bankAccountNumber)) {
    const err: ErrorResponse = {
      success: false,
      error: "Invalid bank account number — must be 9–18 digits",
      code: "INVALID_ACCOUNT",
      timestamp,
    };
    return res.status(400).json(err);
  }

  if (!validateIFSC(body.ifscCode)) {
    const err: ErrorResponse = {
      success: false,
      error: "Invalid IFSC code — expected format: ABCD0123456",
      code: "INVALID_IFSC",
      timestamp,
    };
    return res.status(400).json(err);
  }

  // RTGS minimum: ₹2,00,000
  const mode: TransferMode = body.mode ?? "IMPS";
  if (mode === "RTGS" && body.amount < 200000) {
    const err: ErrorResponse = {
      success: false,
      error: "RTGS requires a minimum transfer amount of ₹2,00,000",
      code: "RTGS_MINIMUM_NOT_MET",
      timestamp,
    };
    return res.status(422).json(err);
  }

  // --- Simulate success/failure ---
  if (!shouldSucceed()) {
    const err: ErrorResponse = {
      success: false,
      error: "Bank network error — transfer could not be processed. Please retry.",
      code: "BANK_NETWORK_ERROR",
      timestamp,
    };
    logger.warn("Simulated bank failure", {
      walletId: body.walletId,
      amount: body.amount,
      mode,
    });
    return res.status(502).json(err);
  }

  const referenceId = `BANK-${mode}-${uuidv4().toUpperCase()}`;
  const transactionId = `TXN-${Date.now()}`;
  const status = initialStatus(mode);

  ledger.add({
    referenceId,
    transactionId,
    walletId: body.walletId,
    amount: body.amount,
    bankAccountNumber: body.bankAccountNumber,
    ifscCode: body.ifscCode,
    mode,
    direction: "DEBIT",
    status,
    remarks: body.remarks,
    createdAt: timestamp,
    updatedAt: timestamp,
  });

  logger.info("Transfer initiated", {
    referenceId,
    walletId: body.walletId,
    amount: body.amount,
    mode,
    status,
  });

  const response: TransferResponse = {
    success: true,
    referenceId,
    transactionId,
    status,
    mode,
    amount: body.amount,
    estimatedSettlement: settlementTime(mode),
    message: buildTransferMessage(mode, body.amount, body.bankAccountNumber),
    timestamp,
  };

  return res.status(201).json(response);
});

function buildTransferMessage(mode: TransferMode, amount: number, account: string): string {
  const maskedAccount = `****${account.slice(-4)}`;
  switch (mode) {
    case "IMPS":
      return `₹${amount.toFixed(2)} transferred instantly to account ${maskedAccount} via IMPS`;
    case "NEFT":
      return `₹${amount.toFixed(2)} queued for NEFT transfer to account ${maskedAccount}. Settles within 2 hours`;
    case "RTGS":
      return `₹${amount.toFixed(2)} queued for RTGS transfer to account ${maskedAccount}. Settles within 30 minutes`;
  }
}
