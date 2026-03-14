import { Router, Request, Response } from "express";
import { v4 as uuidv4 } from "uuid";
import { InitiateCreditRequest, CreditResponse, ErrorResponse, TransferMode } from "../types.js";
import { ledger } from "../ledger.js";
import { logger } from "../logger.js";
import { validateIFSC, validateAccountNumber, shouldSucceed } from "../simulator.js";

export const creditRouter = Router();

/**
 * POST /api/bank/credit
 * Bank account → Wallet (used when user adds money via BANK_TRANSFER)
 */
creditRouter.post("/", (req: Request, res: Response) => {
  const body = req.body as InitiateCreditRequest;
  const timestamp = new Date().toISOString();

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

  if (!shouldSucceed()) {
    const err: ErrorResponse = {
      success: false,
      error: "Could not debit source bank account. Please retry.",
      code: "DEBIT_FAILED",
      timestamp,
    };
    logger.warn("Simulated credit failure", { walletId: body.walletId, amount: body.amount });
    return res.status(502).json(err);
  }

  const mode: TransferMode = body.mode ?? "IMPS";
  const referenceId = `CREDIT-${mode}-${uuidv4().toUpperCase()}`;
  const transactionId = `TXN-${Date.now()}`;

  ledger.add({
    referenceId,
    transactionId,
    walletId: body.walletId,
    amount: body.amount,
    bankAccountNumber: body.bankAccountNumber,
    ifscCode: body.ifscCode,
    mode,
    direction: "CREDIT",
    status: "SUCCESS",
    remarks: body.remarks,
    createdAt: timestamp,
    updatedAt: timestamp,
  });

  logger.info("Credit initiated", {
    referenceId,
    walletId: body.walletId,
    amount: body.amount,
  });

  const response: CreditResponse = {
    success: true,
    referenceId,
    transactionId,
    status: "SUCCESS",
    amount: body.amount,
    message: `₹${body.amount.toFixed(2)} credited to wallet from account ****${body.bankAccountNumber.slice(-4)}`,
    timestamp,
  };

  return res.status(201).json(response);
});
