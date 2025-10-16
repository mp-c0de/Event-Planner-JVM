package mpcode.app.ui

import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.*
import mpcode.domain.Event
import mpcode.domain.Venue
import mpcode.persistence.FileEventRepository
import java.nio.file.Paths
import java.time.Instant

class CreateEventForm : VBox(10.0) {
    private val repo = FileEventRepository(Paths.get("data"))

    private val titleField = TextField()
    private val venueName  = TextField("Main Hall")
    private val capacity   = TextField("100")
    private val startSec   = TextField("3600")  // seconds from now
    private val duration   = TextField("3600")  // seconds

    init {
        padding = Insets(12.0)
        children.addAll(
            grid("Title:", titleField),
            grid("Venue name:", venueName),
            grid("Venue capacity:", capacity),
            grid("Start in secs (from now):", startSec),
            grid("Duration secs:", duration),
            HBox(10.0, Button("Create").apply { setOnAction { onCreate() } })
        )
    }

    private fun onCreate() {
        val t = titleField.text.trim()
        val vn = venueName.text.trim()
        val cap = capacity.text.toIntOrNull()
        val startOffset = startSec.text.toLongOrNull()
        val dur = duration.text.toLongOrNull()

        val errors = buildList {
            if (t.isEmpty()) add("Title is required")
            if (vn.isEmpty()) add("Venue name is required")
            if (cap == null || cap <= 0) add("Venue capacity must be positive")
            if (startOffset == null || startOffset < 0) add("Start offset must be >= 0")
            if (dur == null || dur <= 0) add("Duration must be > 0")
        }
        if (errors.isNotEmpty()) {
            Dialogs.error(errors.joinToString("\n"))
            return
        }

        if (!Dialogs.confirm("Create event “$t”?")) return

        val now = Instant.now()
        val venue = Venue(id = "v1", name = vn, capacity = cap!!)
        val evt = Event(
            id = java.util.UUID.randomUUID().toString(),
            title = t,
            venue = venue,
            start = now.plusSeconds(startOffset!!),
            end = now.plusSeconds(startOffset + dur!!),
            capacity = cap
        )
        repo.save(evt)
        Dialogs.info("Saved: ${evt.title} at ${evt.venue.name}")
    }

    private fun grid(label: String, field: Control): GridPane = GridPane().apply {
        hgap = 8.0; vgap = 6.0; padding = Insets(0.0)
        addRow(0, Label(label), field)
        ColumnConstraints().apply { percentWidth = 30.0 }.also { columnConstraints.add(it) }
        ColumnConstraints().apply { percentWidth = 70.0 }.also { columnConstraints.add(it) }
    }
}
