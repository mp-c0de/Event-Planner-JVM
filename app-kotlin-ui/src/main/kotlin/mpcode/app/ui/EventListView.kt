package mpcode.app.ui

import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.*
import mpcode.domain.Event
import mpcode.persistence.FileEventRepository
import java.nio.file.Paths
import java.time.format.DateTimeFormatter

class EventListView : VBox(10.0) {
    private val repo = FileEventRepository(Paths.get("data"))
    private val list = ListView<String>()
    private val info = Label("")

    init {
        padding = Insets(12.0)
        val refresh = Button("Refresh").apply { setOnAction { load() } }
        children.addAll(HBox(10.0, refresh), list, info)
        VBox.setVgrow(list, Priority.ALWAYS)
        load()
    }

    private fun load() {
        val fmt = DateTimeFormatter.ISO_INSTANT
        val items = repo.findAll().map { e: Event ->
            "${e.title} — ${e.venue.name} — ${fmt.format(e.start)} → ${fmt.format(e.end)} (cap ${e.capacity})"
        }
        list.items.setAll(items)
        info.text = "Total: ${items.size}"
    }
}
