# event-planner-jvm (skeleton)

Multi-module Gradle project with Kotlin (domain/UI/persistence) and Scala 3 (algorithms).

## Modules
- `domain` — Kotlin data classes (Event, Venue, etc.).
- `persistence` — JSON file repo using Jackson.
- `algo-scala` — Scala 3 LTS API (`SlotFinderApi`) called from Kotlin.
- `app-kotlin-ui` — JavaFX 21 app with two buttons.

## Build & run
Ensure JDK 21+ is installed and on PATH.
```bash
./gradlew :app-kotlin-ui:run
```
