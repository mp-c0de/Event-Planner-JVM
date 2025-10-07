package mpcode.app

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage
import mpcode.domain.*
import mpcode.persistence.FileEventRepository
import mpcode.algo.SlotFinderApi
import java.nio.file.Paths
import java.time.Instant
import java.util.UUID

class MainApp : Application() {
    override fun start(stage: Stage) {
        val repo = FileEventRepository(Paths.get("data"))
        val api  = SlotFinderApi()

        val info = Label("Event Planner JVM — skeleton")

        val createBtn = Button("Create sample event").apply {
            setOnAction {
                val now = Instant.now()
                val venue = Venue(id = "v1", name = "Main Hall", capacity = 100)
                val evt = Event(
                    id = EventId(UUID.randomUUID().toString()),
                    title = "Sample Event",
                    venue = venue,
                    start = now.plusSeconds(3600),
                    end = now.plusSeconds(7200),
                    capacity = 80
                )
                repo.save(evt)
                info.text = "Saved event: ${'$'}{evt.title} at ${'$'}{evt.venue.name}"
            }
        }

        val findBtn = Button("Find first available slot (Scala API)").apply {
            setOnAction {
                val now = Instant.now()
                val venue = Venue(id = "v1", name = "Main Hall", capacity = 100)
                val existing = repo.findAll()
                val slot = api.findFirstAvailableSlot(existing, venue, now.plusSeconds(10*60), 60)
                info.text = "First available: ${'$'}{slot[0]} → ${'$'}{slot[1]}"
            }
        }

        val root = VBox(12.0, Label("Event Planner JVM"), createBtn, findBtn, info)
        stage.title = "Event Planner JVM"
        stage.scene = Scene(root, 520.0, 320.0)
        stage.show()
    }
}

fun main() = Application.launch(MainApp::class.java)
