import * as esbuild from "esbuild";
import { argv } from "process";

const isWatch = argv.includes("--watch");

const ctx = await esbuild.context({
  entryPoints: ["src/index.ts"],
  bundle: true,
  platform: "node",
  target: "node20",
  outfile: "dist/index.js",
  sourcemap: true,
  external: [
    // Keep native Node modules external
    "crypto",
    "fs",
    "path",
    "http",
    "https",
    "net",
    "os",
    "stream",
    "util",
    "events",
    "buffer",
    "url",
    "querystring",
    "zlib",
    "tls",
    "dns",
    "child_process",
  ],
  logLevel: "info",
});

if (isWatch) {
  await ctx.watch();
  console.log("Watching for changes...");
} else {
  await ctx.rebuild();
  await ctx.dispose();
}
