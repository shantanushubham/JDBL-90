import { TransferMode, TransactionStatus } from "./types.js";

/**
 * Simulates realistic bank behaviour:
 *  - 5% random failure rate
 *  - NEFT settles in ~2 hours (returns PENDING → async settle)
 *  - IMPS settles immediately (returns SUCCESS)
 *  - RTGS settles in ~30 min (returns PROCESSING → async settle)
 *  - Validates IFSC format (4 alpha + 7 alphanum)
 *  - Validates account number length (9-18 digits)
 */

const IFSC_REGEX = /^[A-Z]{4}0[A-Z0-9]{6}$/;
const ACCOUNT_REGEX = /^\d{9,18}$/;

// Mock IFSC → bank/branch directory
const IFSC_DIRECTORY: Record<string, { bankName: string; branchName: string }> = {
  HDFC0001234: { bankName: "HDFC Bank", branchName: "MG Road, Bangalore" },
  SBIN0001234: { bankName: "State Bank of India", branchName: "Connaught Place, Delhi" },
  ICIC0001234: { bankName: "ICICI Bank", branchName: "Bandra Kurla Complex, Mumbai" },
  AXIS0001234: { bankName: "Axis Bank", branchName: "Koramangala, Bangalore" },
  KKBK0001234: { bankName: "Kotak Mahindra Bank", branchName: "Nariman Point, Mumbai" },
  PUNB0001234: { bankName: "Punjab National Bank", branchName: "Sector 17, Chandigarh" },
};

// Mock account → holder name directory
const ACCOUNT_DIRECTORY: Record<string, string> = {
  "123456789012": "Ravi Kumar",
  "987654321012": "Priya Sharma",
  "112233445566": "Ankit Verma",
  "556677889900": "Sunita Patel",
};

export function validateIFSC(ifsc: string): boolean {
  return IFSC_REGEX.test(ifsc);
}

export function validateAccountNumber(account: string): boolean {
  return ACCOUNT_REGEX.test(account);
}

export function resolveBankInfo(ifsc: string): { bankName: string; branchName: string } {
  return IFSC_DIRECTORY[ifsc] ?? { bankName: "Generic Bank Ltd", branchName: "Main Branch" };
}

export function resolveAccountHolder(accountNumber: string): string {
  return ACCOUNT_DIRECTORY[accountNumber] ?? "Account Holder";
}

/** Simulate whether the transfer succeeds (95% success rate). */
export function shouldSucceed(): boolean {
  return Math.random() > 0.05;
}

/** Return the initial status based on transfer mode. */
export function initialStatus(mode: TransferMode): TransactionStatus {
  switch (mode) {
    case "IMPS":
      return "SUCCESS";
    case "RTGS":
      return "PROCESSING";
    case "NEFT":
      return "PENDING";
  }
}

/** Estimated settlement time description. */
export function settlementTime(mode: TransferMode): string {
  const now = new Date();
  switch (mode) {
    case "IMPS": {
      return now.toISOString(); // immediate
    }
    case "RTGS": {
      now.setMinutes(now.getMinutes() + 30);
      return now.toISOString();
    }
    case "NEFT": {
      now.setHours(now.getHours() + 2);
      return now.toISOString();
    }
  }
}
