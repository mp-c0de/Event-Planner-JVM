package mpcode.app

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import mpcode.domain.*
import mpcode.algo.SlotFinderApi
import java.time.Instant
import javafx.scene.control.TabPane
import javafx.scene.control.Tab
import mpcode.app.ui.CreateEventForm
import mpcode.app.ui.EventListView

class MainApp : Application() {
    override fun start(stage: Stage) {
        val repo = RepositoryManager.eventRepository
        val api  = SlotFinderApi()

        val venueNameField = TextField().apply {
            promptText = "Venue name"
            text = "Main Hall"
        }
        val venueCapField  = TextField().apply {
            promptText = "Venue capacity (int)"
            text = "100"
        }

        val status = Label("Ready")

        val findBtn = Button("Find First Available Slot (Scala)").apply {
            setOnAction {
                val vName = venueNameField.text.ifBlank { "Main Hall" }
                val vCap = venueCapField.text.toIntOrNull()

                if (vCap == null || vCap <= 0) {
                    status.text = "Please enter a valid venue capacity"
                    return@setOnAction
                }

                val venue = Venue(id = vName, name = vName, location = "Default Location", capacity = vCap)
                val existing = repo.findAll()
                val slot = api.findFirstAvailableSlot(existing, venue, Instant.now(), 60)

                val formatter = java.time.format.DateTimeFormatter.ofPattern("MMM dd, HH:mm")
                    .withZone(java.time.ZoneId.systemDefault())
                status.text = "First available slot: ${formatter.format(slot[0])} → ${formatter.format(slot[1])}"
            }
        }

        val grid = GridPane().apply {
            hgap = 8.0; vgap = 8.0; padding = Insets(12.0)
            add(Label("Venue Name:"), 0, 0); add(venueNameField, 1, 0)
            add(Label("Venue Capacity:"), 0, 1); add(venueCapField, 1, 1)
        }

        // Build a concrete root for the "Slot Finder" tab (grid + button + status)
        val actionsRoot = VBox(12.0, grid, findBtn, status).apply {
            padding = Insets(12.0)
        }

        val tabs = TabPane().apply {
            tabs.add(Tab("Slot Finder", actionsRoot).apply { isClosable = false })
            tabs.add(Tab("Events", EventListView()).apply { isClosable = false })
            tabs.add(Tab("Create Event", CreateEventForm()).apply { isClosable = false })
        }

        stage.scene = Scene(tabs, 640.0, 420.0)
        stage.title = "Event Planner JVM"
        stage.show()
    }
}

fun main() = Application.launch(MainApp::class.java)
