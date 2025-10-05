# Week 1 — 06–12 Oct 2025

**Deadlines this week**
- Tue 07 Oct: PRs open by **17:00 UK**; merge to `develop` by **19:00**  
- Fri 10 Oct: W1 DoD PRs open by **17:00**; merge by **19:00**

**Goals (Definition of Done)**
- App builds & runs on Windows/macOS with JDK 21
- UI form creates an event → `data/events.json` appears
- Scala slot finder compiles + 1 passing unit test
- 4 PRs merged into `develop`

---

## Tasks by person

### Martynas (001263199) — Lead + domain/UI integration
- [x] Refactor: use `typealias EventId = String` in `domain/Event.kt`
- [ ] Review teammate PRs; ensure app still runs
- [ ] Add “Windows run” section to README

**Branch:** `feature/domain`  
**Runs:** `./gradlew :app-kotlin-ui:run` (mac) / `.\gradlew.bat :app-kotlin-ui:run` (win)

---

### Rero (001268084) — Scala A-e outline + test
- [ ] Update `algo-scala/build.gradle.kts` with ScalaTest deps + JUnit platform
- [ ] Implement basic non-overlap finder in `SlotFinderApi.scala`
- [ ] Add `SlotFinderApiTest.scala` (1 test that proves it jumps past overlap)

**Branch:** `feature/scala-algo`  
**Test:** `.\gradlew.bat :algo-scala:test`

---

### Cynthia (001299461) — Persistence A-d JSON hardening
- [ ] Add try/catch around read/write in `FileEventRepository.kt`
- [ ] Initialize `data/events.json` if missing
- [ ] Verify “Create event” writes valid JSON

**Branch:** `feature/persistence`  
**Runs:** `.\gradlew.bat :app-kotlin-ui:run` → click “Create event”

---

### Kim (001355492) — UI A-b basic form & wiring
- [ ] Add fields: Title, Venue, Venue capacity, Event capacity
- [ ] Create button saves via repository
- [ ] “Find first available (Scala)” button calls Scala API

**Branch:** `feature/ui`  
**Runs:** `.\gradlew.bat :app-kotlin-ui:run`

---

## Evidence to attach in PRs
- Screenshot of the app window running
- Snippet of `data/events.json` after saving
- Scala test output showing 1 passing test

## Known risks
- JDK mismatch → install **Temurin 21**
- Line endings → repo has `.gitattributes`; if warnings, run `git config --global core.autocrlf false` once

## Retrospective notes (fill Friday)
- What went well:
- What blocked us:
- Actions for Week 2:
