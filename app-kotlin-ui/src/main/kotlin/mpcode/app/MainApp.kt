package mpcode.app

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import mpcode.domain.*
import mpcode.persistence.FileEventRepository
import mpcode.algo.SlotFinderApi
import java.nio.file.Paths
import java.time.Instant
import java.util.UUID
import javafx.scene.control.TabPane
import javafx.scene.control.Tab
import mpcode.app.ui.EventListView

class MainApp : Application() {
    override fun start(stage: Stage) {
        val repo = FileEventRepository(Paths.get("data"))
        val api  = SlotFinderApi()

        val titleField = TextField().apply { promptText = "Event title" }
        val venueNameField = TextField().apply { promptText = "Venue name" }
        val venueCapField  = TextField().apply { promptText = "Venue capacity (int)" }
        val eventCapField  = TextField().apply { promptText = "Event capacity (int)" }

        val status = Label("Ready")

        val createBtn = Button("Create event").apply {
            setOnAction {
                val title = titleField.text.trim()
                val vName = venueNameField.text.trim()
                val vCap  = venueCapField.text.trim()
                val eCap  = eventCapField.text.trim()

                if (title.isEmpty() || vName.isEmpty() || vCap.isEmpty() || eCap.isEmpty()) {
                    status.text = "Fill all fields."
                    return@setOnAction
                }

                val venueCapacity = vCap.toIntOrNull()
                val eventCapacity = eCap.toIntOrNull()
                if (venueCapacity == null || eventCapacity == null) {
                    status.text = "Capacities must be numbers."
                    return@setOnAction
                }

                val venue = Venue(id = "v-${UUID.randomUUID()}", name = vName, capacity = venueCapacity)
                val now = Instant.now()
                val evt = Event(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    venue = venue,
                    start = now.plusSeconds(3600),
                    end = now.plusSeconds(7200),
                    capacity = eventCapacity
                )
                repo.save(evt)
                status.text = "Saved: ${evt.title} at ${venue.name}"
            }
        }

        val findBtn = Button("Find first available (Scala)").apply {
            setOnAction {
                val vName = venueNameField.text.ifBlank { "Main Hall" }
                val venue = Venue(id = "v1", name = vName, capacity = venueCapField.text.toIntOrNull() ?: 100)
                val existing = repo.findAll()
                val slot = api.findFirstAvailableSlot(existing, venue, Instant.now().plusSeconds(10 * 60), 60)
                status.text = "First available: ${slot[0]} → ${slot[1]}"
            }
        }

        val grid = GridPane().apply {
            hgap = 8.0; vgap = 8.0; padding = Insets(12.0)
            add(Label("Title"), 0, 0); add(titleField, 1, 0)
            add(Label("Venue"), 0, 1); add(venueNameField, 1, 1)
            add(Label("Venue capacity"), 0, 2); add(venueCapField, 1, 2)
            add(Label("Event capacity"), 0, 3); add(eventCapField, 1, 3)
        }

        // Build a concrete root for the "Actions" tab (grid + buttons + status)
        val actionsRoot = VBox(12.0, grid, createBtn, findBtn, status).apply {
            padding = Insets(12.0)
        }

        val tabs = TabPane().apply {
            tabs.add(Tab("Actions", actionsRoot).apply { isClosable = false })
            tabs.add(Tab("Events", EventListView()).apply { isClosable = false })
        }

        stage.scene = Scene(tabs, 640.0, 420.0)
        stage.title = "Event Planner JVM"
        stage.show()
    }
}

fun main() = Application.launch(MainApp::class.java)
