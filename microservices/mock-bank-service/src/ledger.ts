import { LedgerEntry, TransactionStatus } from "./types.js";

/**
 * In-memory ledger — simulates the bank's transaction store.
 * Keyed by referenceId for O(1) status lookups.
 */
class Ledger {
  private entries = new Map<string, LedgerEntry>();

  add(entry: LedgerEntry): void {
    this.entries.set(entry.referenceId, entry);
  }

  get(referenceId: string): LedgerEntry | undefined {
    return this.entries.get(referenceId);
  }

  updateStatus(referenceId: string, status: TransactionStatus, reversedBy?: string): boolean {
    const entry = this.entries.get(referenceId);
    if (!entry) return false;
    entry.status = status;
    entry.updatedAt = new Date().toISOString();
    if (reversedBy) entry.reversedBy = reversedBy;
    return true;
  }

  all(): LedgerEntry[] {
    return Array.from(this.entries.values());
  }
}

export const ledger = new Ledger();
