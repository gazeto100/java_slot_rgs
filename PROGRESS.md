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

## Next
- **Step 3 — `ReelStrip`** (circular reel).
