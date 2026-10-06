# Progress

Plan and working rules: see `CLAUDE.md`.

## Environment
- Oracle JDK 21.0.1 (`JAVA_HOME` = `C:\Program Files\Java\jdk-21`, User scope)
- Apache Maven 3.10.0 (`C:\tools\apache-maven-3.10.0`)
- Git 2.42.0
- VS Code + Extension Pack for Java

## Done
- **Step 0 — Environment check.** Installed Maven, pointed `JAVA_HOME` from JRE 8 to JDK 21, installed the Java extension pack.
- **Step 1 — Maven multi-module skeleton.** Parent `pom.xml` (packaging `pom`, Java 21 via `maven.compiler.release`, UTF-8, JUnit BOM 5.13.4, pinned compiler/surefire plugins) and `math-engine` module with JUnit Jupiter (test scope). `.gitignore`. `mvn test` → BUILD SUCCESS, "No tests to run". Git repo initialized, remote `origin` = https://github.com/gazeto100/java_slot_rgs.git.
- **Step 2 — `Symbol` enum.** Plain enum `dev.slotrgs.math.Symbol` with the 9 spec symbols (no fields: pays belong to `Paytable`, WILD/SCATTER behavior comes in steps 8–9). `SymbolTest` guards the exact symbol set and order. `mvn test` → 1 test, BUILD SUCCESS.
- **Extra — `.gitattributes`.** `* text=auto eol=lf`: LF in the repo and in every working copy, regardless of `core.autocrlf`. All files renormalized (they were already LF).
- **Step 3 — `ReelStrip`.** Immutable `record ReelStrip(List<Symbol> symbols)`: defensive `List.copyOf` in the compact constructor, rejects empty/null. `window(stop, rows)` returns the visible symbols top-to-bottom with wrap-around; stop = index of the top row, must be in `[0, size)`; rows in `[1, size]`. `mvn test` → 12 tests, BUILD SUCCESS.
- **Step 4 — `Paytable`.** `record Paytable(Map<Symbol, LinePays> pays)` with nested `record LinePays(three, four, five)` (positive, non-decreasing). Stored as unmodifiable `EnumMap`. `multiplier(symbol, count)` → line-bet multiplier (`int`), 0 for count < 3 or symbols without pays; count must be in `[1, 5]`. Line pays only — scatter pays come in step 9; the actual ClassicFruit20 values are wired in `GameConfig` (step 5). `mvn test` → 30 tests, BUILD SUCCESS.
- **Step 5 — Lines and `GameConfig`.** `record Line(List<Integer> rows)` (row per reel, 0 = top) with `Line.of(int...)` and `rowOn(reel)`. `record GameConfig(reels, rows, paytable, lines)` validates: exactly `REELS` = 5 reels (own constant: reel count is a game property, not the paytable's), rows ≥ 1, each reel ≥ rows symbols, non-empty distinct lines of length 5 with rows in range. `ClassicFruit20` holds the spec constants: `ROWS = 3`, `PAYTABLE`, 20 `LINES` (line shapes chosen by us — spec doesn't fix them; to be confirmed by user). Reel strips not yet defined (steps 11–12). `mvn test` → 53 tests, BUILD SUCCESS.
- **Step 6 — `Rng`.** Interface `Rng.nextInt(bound)` → uniform in `[0, bound)`, IAE for bound ≤ 0. `SecureRng` wraps `SecureRandom` (production, thread-safe). `SeededRng(long seed)` wraps `SplittableRandom` (tests/simulations, not thread-safe). Tests: shared abstract `RngContractTest` (range, all values produced, bound 1, invalid bound) extended by `SecureRngTest` and `SeededRngTest`; the seeded one also checks determinism and a chi-square uniformity test. `mvn test` → 66 tests, BUILD SUCCESS.

## Next
- **Step 7 — `SlotEngine.evaluate()`:** line wins without WILD.
