import express, { Request, Response, NextFunction } from "express";
import { transferRouter } from "./routes/transfer.js";
import { creditRouter } from "./routes/credit.js";
import { accountRouter } from "./routes/account.js";
import { statusRouter } from "./routes/status.js";
import { ledger } from "./ledger.js";
import { logger } from "./logger.js";

const app = express();
const PORT = process.env.PORT ?? 8085;

// ---------------------------------------------------------------------------
// Middleware
// ---------------------------------------------------------------------------

app.use(express.json());

// Request logging
app.use((req: Request, _res: Response, next: NextFunction) => {
  logger.info(`→ ${req.method} ${req.path}`, {
    ip: req.ip,
    body: Object.keys(req.body ?? {}).length ? req.body : undefined,
  });
  next();
});

// Simulate network latency (50–200ms)
app.use((_req: Request, _res: Response, next: NextFunction) => {
  const delay = 50 + Math.random() * 150;
  setTimeout(next, delay);
});

// ---------------------------------------------------------------------------
// Routes
// ---------------------------------------------------------------------------

app.use("/api/bank/transfer", transferRouter);
app.use("/api/bank/credit", creditRouter);
app.use("/api/bank/account", accountRouter);
app.use("/api/bank/status", statusRouter);

/** Health check */
app.get("/health", (_req: Request, res: Response) => {
  res.json({
    status: "UP",
    service: "mock-bank-service",
    timestamp: new Date().toISOString(),
  });
});

/** Admin: view all ledger entries */
app.get("/admin/ledger", (_req: Request, res: Response) => {
  res.json({ total: ledger.all().length, entries: ledger.all() });
});

// 404
app.use((_req: Request, res: Response) => {
  res.status(404).json({ error: "Route not found" });
});

// Global error handler
app.use((err: Error, _req: Request, res: Response, _next: NextFunction) => {
  logger.error("Unhandled error", { message: err.message, stack: err.stack });
  res.status(500).json({ error: "Internal server error", message: err.message });
});

// ---------------------------------------------------------------------------
// Boot
// ---------------------------------------------------------------------------

app.listen(PORT, () => {
  logger.info(`Mock Bank Service running on http://localhost:${PORT}`);
  logger.info("Available endpoints:");
  logger.info("  POST /api/bank/transfer          — Wallet → Bank (NEFT/IMPS/RTGS)");
  logger.info("  POST /api/bank/credit            — Bank → Wallet (add money)");
  logger.info("  POST /api/bank/account/validate  — Validate account + IFSC");
  logger.info("  GET  /api/bank/account/:number   — Account info");
  logger.info("  GET  /api/bank/status/:refId     — Poll transaction status");
  logger.info("  POST /api/bank/status/reverse    — Reverse a transfer");
  logger.info("  GET  /health                     — Health check");
  logger.info("  GET  /admin/ledger               — View all transactions");
});
