// ---------------------------------------------------------------------------
// Enums
// ---------------------------------------------------------------------------

export type TransferMode = "NEFT" | "IMPS" | "RTGS";
export type TransactionStatus = "PENDING" | "PROCESSING" | "SUCCESS" | "FAILED" | "REVERSED";
export type AccountStatus = "ACTIVE" | "FROZEN" | "CLOSED";

// ---------------------------------------------------------------------------
// Request shapes — these are what digital-wallet POSTs to this service
// ---------------------------------------------------------------------------

/** Initiate a bank transfer (wallet → bank account) */
export interface InitiateTransferRequest {
  /** UUID of the wallet/user making the transfer */
  walletId: string;
  /** Amount in INR (must be > 0) */
  amount: number;
  /** Destination bank account */
  bankAccountNumber: string;
  /** IFSC code of the destination bank */
  ifscCode: string;
  /** Account holder name (for validation) */
  accountHolderName?: string;
  /** Transfer mode — defaults to IMPS */
  mode?: TransferMode;
  /** Free-text note (optional) */
  remarks?: string;
}

/** Initiate a credit (bank account → wallet) — used by addMoney BANK_TRANSFER flow */
export interface InitiateCreditRequest {
  /** UUID of the wallet receiving the money */
  walletId: string;
  /** Amount in INR */
  amount: number;
  /** Source bank account number */
  bankAccountNumber: string;
  /** IFSC code of the source bank */
  ifscCode: string;
  /** Transfer mode */
  mode?: TransferMode;
  /** Remarks */
  remarks?: string;
}

/** Validate a bank account before transfer */
export interface ValidateAccountRequest {
  bankAccountNumber: string;
  ifscCode: string;
}

/** Reverse a previously initiated transfer */
export interface ReverseTransferRequest {
  referenceId: string;
  reason: string;
}

// ---------------------------------------------------------------------------
// Response shapes
// ---------------------------------------------------------------------------

export interface TransferResponse {
  success: boolean;
  referenceId: string;
  transactionId: string;
  status: TransactionStatus;
  mode: TransferMode;
  amount: number;
  estimatedSettlement: string;
  message: string;
  timestamp: string;
}

export interface CreditResponse {
  success: boolean;
  referenceId: string;
  transactionId: string;
  status: TransactionStatus;
  amount: number;
  message: string;
  timestamp: string;
}

export interface AccountValidationResponse {
  valid: boolean;
  bankAccountNumber: string;
  ifscCode: string;
  bankName: string;
  branchName: string;
  accountHolderName: string;
  accountStatus: AccountStatus;
  message: string;
}

export interface StatusResponse {
  referenceId: string;
  transactionId: string;
  status: TransactionStatus;
  amount: number;
  mode: TransferMode;
  createdAt: string;
  updatedAt: string;
  message: string;
}

export interface ReverseResponse {
  success: boolean;
  originalReferenceId: string;
  reversalReferenceId: string;
  message: string;
  timestamp: string;
}

export interface ErrorResponse {
  success: false;
  error: string;
  code: string;
  timestamp: string;
}

// ---------------------------------------------------------------------------
// Internal ledger record
// ---------------------------------------------------------------------------

export interface LedgerEntry {
  referenceId: string;
  transactionId: string;
  walletId: string;
  amount: number;
  bankAccountNumber: string;
  ifscCode: string;
  mode: TransferMode;
  direction: "DEBIT" | "CREDIT";
  status: TransactionStatus;
  remarks?: string;
  createdAt: string;
  updatedAt: string;
  reversedBy?: string;
}
