import { Router, Request, Response } from "express";
import { ReverseTransferRequest, StatusResponse, ReverseResponse, ErrorResponse } from "../types.js";
import { ledger } from "../ledger.js";
import { logger } from "../logger.js";
import { v4 as uuidv4 } from "uuid";

export const statusRouter = Router();

/**
 * GET /api/bank/status/:referenceId
 * Poll the status of any transfer or credit by its referenceId.
 */
statusRouter.get("/:referenceId", (req: Request, res: Response) => {
  const { referenceId } = req.params;
  const timestamp = new Date().toISOString();

  const entry = ledger.get(referenceId);
  if (!entry) {
    const err: ErrorResponse = {
      success: false,
      error: `No transaction found with referenceId: ${referenceId}`,
      code: "NOT_FOUND",
      timestamp,
    };
    return res.status(404).json(err);
  }

  // Simulate async settlement: PENDING/PROCESSING entries older than 5s are settled
  const ageMs = Date.now() - new Date(entry.createdAt).getTime();
  if ((entry.status === "PENDING" || entry.status === "PROCESSING") && ageMs > 5000) {
    ledger.updateStatus(referenceId, "SUCCESS");
    entry.status = "SUCCESS";
    logger.info("Async settlement completed", { referenceId });
  }

  const response: StatusResponse = {
    referenceId: entry.referenceId,
    transactionId: entry.transactionId,
    status: entry.status,
    amount: entry.amount,
    mode: entry.mode,
    createdAt: entry.createdAt,
    updatedAt: entry.updatedAt,
    message: `Transaction is ${entry.status.toLowerCase()}`,
  };

  return res.status(200).json(response);
});

/**
 * POST /api/bank/status/reverse
 * Reverse a completed transfer (simulate chargeback / reversal).
 */
statusRouter.post("/reverse", (req: Request, res: Response) => {
  const body = req.body as ReverseTransferRequest;
  const timestamp = new Date().toISOString();

  if (!body.referenceId || !body.reason) {
    const err: ErrorResponse = {
      success: false,
      error: "Missing required fields: referenceId, reason",
      code: "INVALID_REQUEST",
      timestamp,
    };
    return res.status(400).json(err);
  }

  const entry = ledger.get(body.referenceId);
  if (!entry) {
    const err: ErrorResponse = {
      success: false,
      error: `No transaction found with referenceId: ${body.referenceId}`,
      code: "NOT_FOUND",
      timestamp,
    };
    return res.status(404).json(err);
  }

  if (entry.status === "FAILED" || entry.status === "REVERSED") {
    const err: ErrorResponse = {
      success: false,
      error: `Cannot reverse a transaction with status: ${entry.status}`,
      code: "INVALID_STATE",
      timestamp,
    };
    return res.status(422).json(err);
  }

  const reversalReferenceId = `REV-${uuidv4().toUpperCase()}`;
  ledger.updateStatus(body.referenceId, "REVERSED", reversalReferenceId);

  logger.info("Transfer reversed", {
    originalRef: body.referenceId,
    reversalRef: reversalReferenceId,
    reason: body.reason,
  });

  const response: ReverseResponse = {
    success: true,
    originalReferenceId: body.referenceId,
    reversalReferenceId,
    message: `Transfer reversed successfully. Reason: ${body.reason}`,
    timestamp,
  };

  return res.status(200).json(response);
});
