package mpcode.app.ui

import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.*
import mpcode.app.RepositoryManager
import mpcode.domain.Event
import mpcode.domain.Venue
import java.time.Instant

class CreateEventForm : VBox(10.0) {
    private val repo = RepositoryManager.eventRepository

    private val titleField = TextField().apply {
        promptText = "Enter event title"
    }
    private val venueName = TextField().apply {
        text = "Main Hall"
        promptText = "Enter venue name"
    }
    private val venueLocation = TextField().apply {
        text = "Building A"
        promptText = "Enter venue location"
    }
    private val capacity = TextField().apply {
        text = "100"
        promptText = "Enter capacity (number)"
    }
    private val startSec = TextField().apply {
        text = "3600"
        promptText = "Seconds from now (e.g. 3600 = 1 hour)"
    }
    private val duration = TextField().apply {
        text = "3600"
        promptText = "Duration in seconds (e.g. 3600 = 1 hour)"
    }

    init {
        padding = Insets(16.0)

        val formTitle = Label("Create New Event").apply {
            style = "-fx-font-size: 16px; -fx-font-weight: bold;"
        }

        children.addAll(
            formTitle,
            Separator(),
            grid("Event Title:", titleField),
            grid("Venue Name:", venueName),
            grid("Venue Location:", venueLocation),
            grid("Venue Capacity:", capacity),
            grid("Start Time (seconds from now):", startSec),
            grid("Duration (seconds):", duration),
            Separator(),
            HBox(10.0).apply {
                val createBtn = Button("Create Event").apply {
                    setOnAction { onCreate() }
                    style = "-fx-base: #4CAF50;"
                }
                val clearBtn = Button("Clear Form").apply {
                    setOnAction { clearForm() }
                }
                children.addAll(createBtn, clearBtn)
            }
        )
    }

    private fun onCreate() {
        val t = titleField.text.trim()
        val vn = venueName.text.trim()
        val vl = venueLocation.text.trim()
        val cap = capacity.text.toIntOrNull()
        val startOffset = startSec.text.toLongOrNull()
        val dur = duration.text.toLongOrNull()

        val errors = buildList {
            if (t.isEmpty()) add("Title is required")
            if (vn.isEmpty()) add("Venue name is required")
            if (vl.isEmpty()) add("Venue location is required")
            if (cap == null || cap <= 0) add("Venue capacity must be positive")
            if (startOffset == null || startOffset < 0) add("Start offset must be >= 0")
            if (dur == null || dur <= 0) add("Duration must be > 0")
        }
        if (errors.isNotEmpty()) {
            Dialogs.error(errors.joinToString("\n"))
            return
        }

        if (!Dialogs.confirm("Create event '$t'?")) return

        val now = Instant.now()
        val venue = Venue(id = "v-${java.util.UUID.randomUUID()}", name = vn, location = vl, capacity = cap!!)
        val evt = Event(
            id = "e-${java.util.UUID.randomUUID()}",
            title = t,
            venue = venue,
            start = now.plusSeconds(startOffset!!),
            end = now.plusSeconds(startOffset + dur!!),
            capacity = cap
        )
        repo.save(evt)
        Dialogs.info("Event created successfully!\n\n${evt.title}\n${evt.venue.name} (${evt.venue.location})")
        clearForm()
    }

    private fun clearForm() {
        titleField.clear()
        venueName.text = "Main Hall"
        venueLocation.text = "Building A"
        capacity.text = "100"
        startSec.text = "3600"
        duration.text = "3600"
        titleField.requestFocus()
    }

    private fun grid(label: String, field: Control): GridPane = GridPane().apply {
        hgap = 10.0; vgap = 8.0; padding = Insets(4.0, 0.0, 4.0, 0.0)
        addRow(0, Label(label), field)
        ColumnConstraints().apply {
            minWidth = 200.0
            prefWidth = 200.0
        }.also { columnConstraints.add(it) }
        ColumnConstraints().apply {
            hgrow = Priority.ALWAYS
        }.also { columnConstraints.add(it) }
    }
}
