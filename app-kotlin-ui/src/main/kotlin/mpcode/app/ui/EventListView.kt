package mpcode.app.ui

import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.*
import mpcode.app.RepositoryManager
import mpcode.domain.Event
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class EventListView : VBox(10.0) {
    private val repo = RepositoryManager.eventRepository
    private val list = ListView<Event>()
    private val info = Label("")

    private val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        .withZone(ZoneId.systemDefault())
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())

    init {
        padding = Insets(12.0)

        // Configure list view with custom cell factory for better formatting
        list.setCellFactory {
            object : ListCell<Event>() {
                override fun updateItem(event: Event?, empty: Boolean) {
                    super.updateItem(event, empty)
                    if (empty || event == null) {
                        text = null
                        graphic = null
                    } else {
                        val dateStr = dateFormatter.format(event.start)
                        val startTime = timeFormatter.format(event.start)
                        val endTime = timeFormatter.format(event.end)

                        text = """
                            ${event.title}
                            Venue: ${event.venue.name} (${event.venue.location})
                            Date: $dateStr, $startTime - $endTime
                            Capacity: ${event.capacity}/${event.venue.capacity}
                        """.trimIndent()
                    }
                }
            }
        }

        val buttonBox = HBox(10.0).apply {
            val refreshBtn = Button("Refresh").apply { setOnAction { load() } }
            val viewDetailsBtn = Button("View Details").apply {
                setOnAction {
                    list.selectionModel.selectedItem?.let { showEventDetails(it) }
                }
            }
            children.addAll(refreshBtn, viewDetailsBtn)
        }

        children.addAll(buttonBox, list, info)
        VBox.setVgrow(list, Priority.ALWAYS)
        load()
    }

    private fun load() {
        val events = repo.findAll().sortedBy { it.start }
        list.items.setAll(events)
        info.text = if (events.isEmpty()) {
            "No events scheduled"
        } else {
            "Total events: ${events.size}"
        }
    }

    private fun showEventDetails(event: Event) {
        val dateStr = dateFormatter.format(event.start)
        val startTime = timeFormatter.format(event.start)
        val endTime = timeFormatter.format(event.end)

        val details = """
            Event: ${event.title}

            Venue: ${event.venue.name}
            Location: ${event.venue.location}
            Venue Capacity: ${event.venue.capacity}

            Date: $dateStr
            Time: $startTime - $endTime
            Event Capacity: ${event.capacity}

            Event ID: ${event.id}
        """.trimIndent()

        Dialogs.info(details)
    }
}
