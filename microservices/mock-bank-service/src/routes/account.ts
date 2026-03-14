import { Router, Request, Response } from "express";
import {
  ValidateAccountRequest,
  AccountValidationResponse,
  ErrorResponse,
} from "../types.js";
import {
  validateIFSC,
  validateAccountNumber,
  resolveBankInfo,
  resolveAccountHolder,
} from "../simulator.js";

export const accountRouter = Router();

/**
 * POST /api/bank/account/validate
 * Validates a bank account + IFSC before initiating a transfer.
 * Returns account holder name (penny-drop simulation).
 */
accountRouter.post("/validate", (req: Request, res: Response) => {
  const body = req.body as ValidateAccountRequest;
  const timestamp = new Date().toISOString();

  if (!body.bankAccountNumber || !body.ifscCode) {
    const err: ErrorResponse = {
      success: false,
      error: "Missing required fields: bankAccountNumber, ifscCode",
      code: "INVALID_REQUEST",
      timestamp,
    };
    return res.status(400).json(err);
  }

  if (!validateAccountNumber(body.bankAccountNumber)) {
    const response: AccountValidationResponse = {
      valid: false,
      bankAccountNumber: body.bankAccountNumber,
      ifscCode: body.ifscCode,
      bankName: "",
      branchName: "",
      accountHolderName: "",
      accountStatus: "CLOSED",
      message: "Account number is invalid — must be 9–18 digits",
    };
    return res.status(200).json(response);
  }

  if (!validateIFSC(body.ifscCode)) {
    const response: AccountValidationResponse = {
      valid: false,
      bankAccountNumber: body.bankAccountNumber,
      ifscCode: body.ifscCode,
      bankName: "",
      branchName: "",
      accountHolderName: "",
      accountStatus: "CLOSED",
      message: "IFSC code is invalid — expected format: ABCD0123456",
    };
    return res.status(200).json(response);
  }

  const { bankName, branchName } = resolveBankInfo(body.ifscCode);
  const accountHolderName = resolveAccountHolder(body.bankAccountNumber);

  const response: AccountValidationResponse = {
    valid: true,
    bankAccountNumber: body.bankAccountNumber,
    ifscCode: body.ifscCode,
    bankName,
    branchName,
    accountHolderName,
    accountStatus: "ACTIVE",
    message: "Account is valid and active",
  };

  return res.status(200).json(response);
});

/**
 * GET /api/bank/account/:accountNumber
 * Fetch basic account info.
 */
accountRouter.get("/:accountNumber", (req: Request, res: Response) => {
  const { accountNumber } = req.params;
  const timestamp = new Date().toISOString();

  if (!validateAccountNumber(accountNumber)) {
    const err: ErrorResponse = {
      success: false,
      error: "Invalid account number format",
      code: "INVALID_ACCOUNT",
      timestamp,
    };
    return res.status(400).json(err);
  }

  const holderName = resolveAccountHolder(accountNumber);
  return res.status(200).json({
    bankAccountNumber: accountNumber,
    accountHolderName: holderName,
    accountStatus: "ACTIVE",
  });
});
