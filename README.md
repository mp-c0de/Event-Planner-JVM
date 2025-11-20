# Event Planning Application

**Course:** COMP1815 - JVM Languages
**University:** University of Greenwich
**Academic Year:** 2024-25
**Group:** Group 30

---

## 📋 Overview

A multi-module event planning application demonstrating **polyglot JVM development** with Kotlin, Scala 3, and JavaFX. The application provides a graphical interface for managing events and venues with intelligent slot-finding algorithms.

### ✨ Key Features

- **Multi-language Architecture** - Kotlin (UI & Domain), Scala 3 (Algorithms)
- **JavaFX 21 GUI** - Modern desktop interface
- **Gradle Multi-module Build** - Clean separation of concerns
- **JSON Persistence** - File-based data storage with Jackson
- **Functional Programming** - Scala 3 LTS for algorithm implementation
- **Domain-Driven Design** - Clear domain model with data classes

---

## 🛠️ Technical Stack

- **Languages:** Kotlin, Scala 3 LTS, Java
- **GUI Framework:** JavaFX 21
- **Build Tool:** Gradle 8.x with Kotlin DSL
- **JSON Library:** Jackson
- **JVM:** JDK 21+

---

## 📁 Project Structure

```
event-planner-jvm/
├── domain/                      # Kotlin domain model
│   └── Event, Venue data classes
├── persistence/                 # JSON repository layer
│   └── Jackson-based file storage
├── algo-scala/                  # Scala 3 algorithms
│   └── SlotFinderApi implementation
├── app-kotlin-ui/               # JavaFX application
│   └── Main UI with Kotlin
├── data/                        # JSON data files (gitignored)
└── build.gradle.kts             # Root build configuration
```

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 21+**
- **Gradle** (wrapper included)

### Building the Project

#### On macOS/Linux:
```bash
./gradlew build
```

#### On Windows:
```bash
.\gradlew.bat build
```

### Running the Application

#### On macOS/Linux:
```bash
./gradlew :app-kotlin-ui:run
```

#### On Windows:
```bash
.\gradlew.bat :app-kotlin-ui:run
```

---

## 🏗️ Architecture

### Module Dependencies

```
app-kotlin-ui  →  algo-scala  →  domain
                ↘  persistence  →  domain
```

### Design Patterns

- **Repository Pattern** - JSON file persistence abstraction
- **Facade Pattern** - Scala API called from Kotlin
- **Domain Model** - Immutable data classes in Kotlin
- **Functional Programming** - Scala 3 for algorithm logic

---

## 🎓 Learning Outcomes

This project demonstrates:

- **Polyglot JVM Development** - Seamless interop between Kotlin, Scala, and Java
- **Multi-module Architecture** - Clean separation of concerns with Gradle
- **Functional Programming** - Scala 3 LTS for algorithmic implementation
- **GUI Development** - JavaFX with Kotlin
- **Build Automation** - Gradle with Kotlin DSL
- **Data Persistence** - JSON serialisation with Jackson

---

## 🧪 Module Descriptions

### `domain`
Kotlin data classes representing the core domain model:
- `Event` - Event entity with properties
- `Venue` - Venue entity with capacity and location
- Immutable by default (Kotlin data classes)

### `persistence`
JSON-based repository layer using Jackson:
- File-based storage
- Read/write operations
- Data serialisation/deserialisation

### `algo-scala`
Scala 3 LTS implementation of slot-finding algorithms:
- `SlotFinderApi` - Main API interface
- Functional approach to algorithm design
- Called from Kotlin via JVM interop

### `app-kotlin-ui`
JavaFX desktop application in Kotlin:
- Main UI window
- Event handlers
- Integration with Scala algorithms and persistence

---

## 📝 Development Notes

### Tested Environment
- **OS:** macOS Sequoia
- **JDK:** OpenJDK 21+
- **Gradle:** 8.x with Kotlin DSL

### Known Limitations
- Data stored in local JSON files (no database)
- Basic UI with two main buttons
- No authentication or user management

### Future Enhancements
- Add database persistence (PostgreSQL/SQLite)
- Implement REST API layer
- Add comprehensive test suite
- Enhance UI with more features
- Add CI/CD pipeline

---

## 👥 Group Members

This was a collaborative group project developed as part of COMP1815 coursework.

---

## 📄 License

This project was created for academic purposes as part of university coursework.

---

## 👤 Author

**Martynas Prascevicius**
📧 Email: mpcode@icloud.com
💼 LinkedIn: [linkedin.com/in/mpc0de](https://linkedin.com/in/mpc0de)
🐙 GitHub: [github.com/mp-c0de](https://github.com/mp-c0de)

---

*Built for COMP1815 - JVM Languages coursework at University of Greenwich*
