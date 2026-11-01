<!--
  STOP. Check the base repository named at the top of this page.

  It must be YOUR fork, under your own GitHub username. If it names an owner of
  tcu-cosc-40943, you have opened this against the class repository: close it,
  press New pull request again, and change the base repository dropdown to your
  fork and the base branch to main. A pull request against the class repository
  is not your submission.

  Keep each section short; tables over paragraphs. These comments do not appear
  in the posted description, so leave them or delete them as you like.
-->

Closes #  <!-- your "Triage slice N" issue -->
Slice: <!-- the digit and name from the assignment page -->
Traces to: none: config

## Project decisions
<!-- D1 to D4: enforce, turn off, or narrow; the reason someone could check;
     the file you changed (spotbugs-exclude.xml, pmd-ruleset.xml, or the code). -->

| | Rule | Choice | Reason | Where recorded |
|---|---|---|---|---|
| D1 | EI_EXPOSE_REP, EI_EXPOSE_REP2 | | | |
| D2 | UncommentedEmptyConstructor, UnnecessaryConstructor | | | |
| D3 | ControlStatementBraces | | | |
| D4 | GuardLogStatement | | | |

## My slice
<!-- One row per finding in your slice. Verdict: real, false positive, or not worth it.
     Recorded as: the fix commit, or the suppression (at the site, with its reason). -->

| File:line | Rule | Verdict | Recorded as | Reason |
|---|---|---|---|---|
| | | | | |

## The bug I showed
<!-- The test, the commit where it fails (before the fix), the commit where it
     passes. Or, if no finding in your slice can be shown by a test, why. -->

## The baselines
<!-- Entries in each file, before and after. Neither may grow. Frontend slices:
     the vue-tsc and ESLint counts from the CI log, before and after. -->

| | Before | After |
|---|---|---|
| spotbugs-baseline.xml | 237 | |
| pmd-baseline.properties | 130 | |

## What the agent did
<!-- What it proposed, what you kept, what you rejected or rewrote, and why.
     If you used no agent, say so. -->

## How it was verified
- **Verified:** <!-- the checks on this pull request; what you ran locally -->
- **Not verified:** <!-- what nobody checked. An honest answer here is never penalized. -->
