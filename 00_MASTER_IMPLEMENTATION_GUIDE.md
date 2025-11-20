# Week 4 Final Implementation - Master Guide
**Course:** COMP1815 - JVM Programming Languages
**Deadline:** Monday, 01/12/2025 17:00 UK Time
**Group:** Group 30

---

## 📋 Overview

This document coordinates the final implementation tasks to complete the coursework. The work is split into **4 sequential tasks** among 4 team members. Each person has a numbered ZIP file containing their specific code and instructions.

**IMPORTANT:** Tasks must be completed **in order** (1 → 2 → 3 → 4) because each builds on the previous work.

---

## 🎯 Current Status

### ✅ Already Complete on `develop` Branch:
- a) Domain Classes - Kotlin ✅
- b) Event Creation Application - Kotlin ✅
- d) Persistence - Kotlin ✅
- e) Slot Finder - Scala ✅
- g) Version Control - Git ✅

### ❌ Missing (What We're Adding):
- c) Participant Registration - Kotlin ❌ (Tasks 1 & 2)
- f) Event Scheduler Integration ❌ (Task 3)
- Bug fixes (Task 4)

---

## 👥 Team Members & Tasks

| # | Person | Task | Files | Time | Branch |
|---|--------|------|-------|------|--------|
| 1 | Martynas Prascevicius | ParticipantManagementView | `1_Martynas_W4_Final.zip` | 30-45 min | feature/participant-management |
| 2 | Kim Le | RegistrationView | `2_Kim_W4_Final.zip` | 45-60 min | feature/registration |
| 3 | Cynthia Kalu | ScheduleGeneratorView | `3_Cynthia_W4_Final.zip` | 30-40 min | feature/schedule-generator |
| 4 | Rerosuoghene Gaius Eppiah | Bug Fixes + Tests | `4_Rero_W4_Final.zip` | 30-40 min | fix/venue-id-and-tests |

**Total Time:** ~2.5-3 hours for all 4 tasks (sequentially)

---

## 🔄 Workflow

### Phase 1: Martynas (Task 1)
1. Create branch from `develop`: `feature/participant-management`
2. Extract `1_Martynas_W4_Final.zip`
3. Follow instructions in `1_Martynas_Instructions.md`
4. Add ParticipantManagementView to project
5. Integrate into MainApp
6. Test, commit, push
7. **Notify Kim when done**

### Phase 2: Kim (Task 2)
1. Pull Martynas's branch
2. Create branch from Martynas's work: `feature/registration`
3. Extract `2_Kim_W4_Final.zip`
4. Follow instructions in `2_Kim_Instructions.md`
5. Add RegistrationView to project
6. Integrate into MainApp
7. Test capacity validation!
8. Test, commit, push
9. **Notify Cynthia when done**

### Phase 3: Cynthia (Task 3)
1. Pull Kim's branch
2. Create branch from Kim's work: `feature/schedule-generator`
3. Extract `3_Cynthia_W4_Final.zip`
4. Follow instructions in `3_Cynthia_Instructions.md`
5. Add ScheduleGeneratorView to project
6. Integrate into MainApp
7. Test Scala integration!
8. Test, commit, push
9. **Notify Rero when done**

### Phase 4: Rero (Task 4)
1. Pull Cynthia's branch
2. Create branch from Cynthia's work: `fix/venue-id-and-tests`
3. Extract `4_Rero_W4_Final.zip`
4. Follow instructions in `4_Rero_Instructions.md`
5. Fix venue ID bug in CreateEventForm
6. Add SchedulerApiTest
7. Run all tests (must pass!)
8. Test, commit, push
9. **Create Pull Request to merge into `develop`**

---

## 📦 ZIP File Contents

### `1_Martynas_W4_Final.zip`
- `ParticipantManagementView.kt` (143 lines)
- `1_Martynas_Instructions.md` (detailed guide)
- `MainApp_Integration_Task1.txt` (code snippets)

### `2_Kim_W4_Final.zip`
- `RegistrationView.kt` (185 lines)
- `2_Kim_Instructions.md` (detailed guide)
- `MainApp_Integration_Task2.txt` (code snippets)

### `3_Cynthia_W4_Final.zip`
- `ScheduleGeneratorView.kt` (175 lines)
- `3_Cynthia_Instructions.md` (detailed guide)
- `MainApp_Integration_Task3.txt` (code snippets)

### `4_Rero_W4_Final.zip`
- `CreateEventForm_Fixed.kt` (complete file)
- `SchedulerApiTest.scala` (Scala test)
- `4_Rero_Instructions.md` (detailed guide)
- `Bug_Explanation.md` (explains venue ID bug)

---

## ✅ Final Checklist (After All 4 Tasks)

After Rero completes Task 4, the application should have:

### UI Components (6 tabs):
1. ✅ Slot Finder (SlotFinderApi demo)
2. ✅ Events (list all events)
3. ✅ Create Event (event creation form)
4. ✅ Participants (add/list/delete) ← **NEW (Martynas)**
5. ✅ Register (register participants with capacity check) ← **NEW (Kim)**
6. ✅ Schedule (generate conflict-free schedule) ← **NEW (Cynthia)**

### Coursework Requirements:
- ✅ a) Domain Classes - Kotlin
- ✅ b) Event Creation App - Kotlin
- ✅ c) Participant Registration - Kotlin ← **COMPLETED by Tasks 1 & 2**
- ✅ d) Persistence - Kotlin
- ✅ e) Slot Finder - Scala
- ✅ f) Event Scheduler - Scala ← **INTEGRATED by Task 3**
- ✅ g) Version Control - Git

### Tests:
- ✅ SlotFinderApiTest (already exists)
- ✅ FileEventRepositoryTest (already exists)
- ✅ SchedulerApiTest ← **NEW (Rero)**

### Bug Fixes:
- ✅ Venue ID fixed ← **FIXED (Rero)**

---

## 🚨 Critical Notes

### DO:
✅ Follow the numbered order (1 → 2 → 3 → 4)
✅ Test your work before committing
✅ Follow the exact integration steps in each guide
✅ Notify the next person when you're done
✅ Make sure builds succeed
✅ Run the application to verify

### DON'T:
❌ Skip ahead or work out of order
❌ Modify files not mentioned in your task
❌ Add extra features not in the instructions
❌ Push to `develop` directly (use branches!)
❌ Forget to test capacity validation (Kim!)
❌ Forget to test venue ID fix (Rero!)

---

## 🔧 Common Commands

### Start Your Task:
```bash
git switch develop
git pull origin develop
git switch -c feature/your-branch-name
# Extract your ZIP file
# Follow your instructions
```

### Test & Commit:
```bash
./gradlew build
./gradlew :app-kotlin-ui:run  # Test the UI
git add <files>
git commit -m "your message"
git push -u origin your-branch-name
```

### After All Tasks Complete:
```bash
# Rero creates final PR
git push -u origin fix/venue-id-and-tests
# Then create PR on Azure DevOps to merge into develop
```

---

## 📞 Communication

**Slack/Teams Channel:** Use for coordination
**Order of Notifications:**
1. Martynas → Kim ("Task 1 done, branch pushed")
2. Kim → Cynthia ("Task 2 done, branch pushed")
3. Cynthia → Rero ("Task 3 done, branch pushed")
4. Rero → Everyone ("All tasks done, PR created!")

---

## 🎯 Success Criteria

### After All Tasks:
- ✅ All 7 coursework requirements met
- ✅ Application runs without errors
- ✅ All tabs work correctly
- ✅ Capacity validation works
- ✅ Scala integration works
- ✅ All tests pass
- ✅ No duplicate/redundant code
- ✅ Venue ID bug fixed

### Ready for:
- Individual reports (Part B)
- Demonstration (Part C)
- Final submission

---

## 📅 Timeline Suggestion

**Day 1 (Today):**
- Martynas: Task 1 (30-45 min)
- Kim: Task 2 (45-60 min)

**Day 2:**
- Cynthia: Task 3 (30-40 min)
- Rero: Task 4 (30-40 min)
- Code review & testing

**Day 3:**
- Merge to develop
- Final testing
- Start individual reports

---

## ❓ Questions?

1. **"My task depends on previous task - when do I start?"**
   - Wait for notification from previous person
   - Pull their branch first
   - Then start your work

2. **"Build fails after adding my code?"**
   - Check you copied files to correct location
   - Check you added correct imports to MainApp
   - Run `./gradlew clean build`

3. **"Tests failing?"**
   - Make sure you're on the correct branch
   - Make sure all previous tasks are merged
   - Check the error message carefully

4. **"Can I add extra features?"**
   - NO! Stick to the exact requirements
   - We need to meet coursework spec, not exceed it
   - Extra features might cause issues

---

## 🏁 Final Notes

- This is **NOT optional** - we need all 4 tasks for a complete submission
- Tasks are **sequential** - don't skip or reorder
- Each task is **tested** - code is proven to work
- Each guide is **detailed** - follow step by step
- **Communication is key** - notify the next person!

---

**Good luck team! Let's finish this coursework strong! 💪**

---

*Generated: November 1, 2025*
*Branch: develop*
*Target: 100% coursework completion*
