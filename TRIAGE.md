# Triage slice 6: Requirements (RAM)

## Project decisions

| Decision | Choice | Reason and implementation |
|---|---|---|
| D1: `EI_EXPOSE_REP`, `EI_EXPOSE_REP2` | Narrow | `backend/config/spotbugs-exclude.xml` lists the existing JPA entity classes, specific Spring injection constructors, and `MyUserPrincipal`. Entities share managed associations, injection retains shared beans, and the principal adapts the user entity read by `AuthService` and `JwtProvider`. Other classes remain checked. Collection snapshots resolve the other 46 findings, preserving null collections and null elements. |
| D2: `UncommentedEmptyConstructor`, `UnnecessaryConstructor` | Enforce | Required empty constructors explain their JPA or bean-construction purpose. Redundant public no-argument constructors are removed; Java supplies the same public constructor when no others exist. Constructors needed alongside parameterized constructors remain. |
| D3: `ControlStatementBraces` | Enforce | Add braces to all 30 baselined control statements so later additions have an explicit scope. |
| D4: `GuardLogStatement` | Turn off | The four findings use parameterized SLF4J logging with cheap getters, array length, or collection size. Message formatting is deferred already. Extra guards save negligible argument work here. The reason is recorded in `backend/config/pmd-ruleset.xml`. |

D1 covers 219 findings: 144 in entities, 27 in injected-dependency constructors, two in the principal, and 46 resolved through collection snapshots. Copies protect collection structure, not mutable elements. The weekly report is assembled before its list is assigned, rather than modified through its getter. No copying is imposed on JPA association accessors.

## My slice

Locations below refer to the assignment's starting commit, `d397bb1`, under `backend/src/main/java/team/projectpulse/`.

| File:line | Rule | Verdict | Recorded as | Reason |
|---|---|---|---|---|
| `ram/document/DocumentSectionLock.java:40` | `SimplifyBooleanReturns` | Not worth it | Method-scoped suppression and explanatory comment | The separate unlocked, manual-lock, and timed-lock cases make lease behavior easier to maintain. |
| `ram/document/RequirementDocumentSpecs.java:5` | `InstantiableUtilityClass` | Real | Private constructor; final class | Static query helpers have no instance behavior and are not framework-managed entities or beans. |
| `ram/document/RequirementDocumentSpecs.java:19` | `UseLocaleWithCaseConversions` | Real | `Locale.ROOT`; regression test | The same title search must generate the same pattern regardless of the server's default locale. |
| `ram/usecase/UseCaseLock.java:40` | `SimplifyBooleanReturns` | Not worth it | Method-scoped suppression and explanatory comment | Keep the three lock states explicit, for the same reason as document locks. |
| `ram/usecase/UseCaseService.java:251` | `UnusedPrivateMethod` | Real | Delete `updateCollection` | The introducing commit, `72068f3`, already updates collections directly. The helper has no callers; removing it preserves behavior. |
| `ram/usecase/UseCaseController.java:33` | `DanglingJavadoc` | Real | Ordinary implementation comment | A Javadoc block between statements attaches to no API declaration. Preserve the useful explanation as a normal comment. |
| `ram/requirement/ArtifactLinkSpecs.java:5` | `InstantiableUtilityClass` | Real | Private constructor; final class | This class only supplies static query factories. |
| `ram/requirement/RequirementArtifactSpecs.java:5` | `InstantiableUtilityClass` | Real | Private constructor; final class | This class only supplies static query factories. |

## The bug I showed

`RequirementDocumentSpecsTest.titleSearchUsesTheSamePatternUnderTurkishAndEnglishLocales` executes the actual specification with a mocked Criteria API. It checks that both English and Turkish default locales generate `%interface%` for `INTERFACE`. Before the fix, Turkish produces a dotless `ı`, so the expected predicate is built only once rather than twice. The original default locale is restored in `finally`.

- Test-first commit: `cdc6cb2`. The targeted Maven run failed with one assertion failure and no errors.
- Fix commit: `234f537`. The same targeted run passed: one test, zero failures or errors.
- Command from `backend/`: `mvnw.cmd test -Dtest=RequirementDocumentSpecsTest`.

This test proves locale-independent predicate construction; it does not execute a database query or establish Unicode behavior for every database collation.

## Baselines

| Measure | Before | After |
|---|---:|---:|
| SpotBugs XML finding entries | 237 | 18 |
| PMD findings represented by the baseline | 130 | 48 |
| PMD stored class/rule pairs | 101 | 36 |

The PMD properties format can cover several occurrences with a single class/rule pair. The 48 remaining findings are 130 minus 74 project-decision findings and eight slice findings. No new SpotBugs entries or PMD class/rule pairs were added.

## What the agent did

Codex inspected source, history, configurations, and the course guidance; proposed the decisions and verdicts; and implemented the changes after the user's permission. It wrote the regression and collection-behavior tests and ran the checks.

The implementation avoids blanket defensive copies on entities, removes no required JPA constructor, and preserves nullable collection values instead of using null-rejecting `List.copyOf`. The lock methods retain their explicit cases instead of accepting a purely stylistic boolean rewrite. These are agent recommendations accepted for implementation; the student should review and be able to explain them before submission.

## Verification

- The locale regression was observed failing before its fix and passing afterward.
- Five collection tests cover input aliasing, read-only access, nullable collections, null elements, report assembly, and template sections.
- With Java 21, `mvnw.cmd verify -DexcludedGroups=integration "-Dtest=!WebConfigSpaForwardingTest" -B -ntp` passed: 131 tests, zero failures or errors, and both SpotBugs and PMD gates passed. `WebConfigSpaForwardingTest` also requires the shared Docker containers, so it is explicitly excluded alongside the integration tag. This is a partial verification command, not a substitute for full CI.
- After Docker became available, the full Java 21 `mvnw.cmd verify -B -ntp` passed: **445 tests, zero failures, zero errors, zero skipped**, followed by passing SpotBugs and PMD gates. MySQL and Mailpit test containers started successfully. The run took 13 minutes 12 seconds. This supersedes the earlier attempt that was blocked by Docker startup failures.
- Frontend: `npm ci` succeeded; ESLint reports 128 errors and two warnings; type checking reports 18 errors; Prettier reports 75 files. No frontend source was changed.
- GitHub Actions, the GitHub issue, the pull request, and Dockerfile CI lint have not been verified or created by this local implementation.

## Submission mechanics still required

Create an issue titled **Triage slice 6** in your fork, identifying this slice and D1-D4. Open a pull request from `codex/triage-slice-6` to your fork's `main`, use the provided PR template, and link the issue. Copy the relevant tables and evidence from this file. Review the agent-contribution description in your own words. Enable workflows, obtain passing required CI checks, leave the PR open, and submit its URL. Do not merge it.
