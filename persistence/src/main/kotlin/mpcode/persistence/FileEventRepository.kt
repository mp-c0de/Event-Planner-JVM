package mpcode.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mpcode.domain.Event
import mpcode.domain.EventId
import java.io.File
import java.nio.file.Path
import java.time.Instant

class FileEventRepository(dataDir: Path) : EventRepository {
    private val mapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())

    private val file: File = dataDir.resolve("events.json").toFile()

    init {
        file.parentFile?.mkdirs()
        if (!file.exists()) file.writeText("[]")
    }

    // --- helpers -------------------------------------------------------------

    private fun writeAll(events: List<Event>) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, events)
        } catch (e: Exception) {
            println("ERROR events.json write: ${e.message}")
            throw e
        }
    }

    private fun readAll(): List<Event> {
        if (!file.exists()) return emptyList()
        val typeRef = object : TypeReference<List<Event>>() {}
        return try {
            mapper.readValue(file, typeRef)
        } catch (e: Exception) {
            println("ERROR events.json read: ${e.message}")
            emptyList()
        }
    }

    // --- Week 1 methods ------------------------------------------------------

    override fun save(event: Event) {
        // (simple append – Week 1 contract)
        val all = readAll().toMutableList()
        all.add(event)
        writeAll(all)
    }

    override fun findAll(): List<Event> = readAll()

    override fun findById(id: EventId): Event? =
        readAll().find { it.id == id }

    // --- Week 2 additions ----------------------------------------------------

    /** Replace existing event (by id). Returns true if an event was updated. */
    fun update(event: Event): Boolean {
        require(event.start.isBefore(event.end)) { "start must be before end" }
        val all = readAll().toMutableList()
        val idx = all.indexOfFirst { it.id == event.id }
        if (idx < 0) return false
        all[idx] = event
        writeAll(all)
        return true
    }

    /** Delete by id. Returns true if something was removed. */
    fun deleteById(id: EventId): Boolean {
        val before = readAll()
        val after = before.filterNot { it.id == id }
        if (after.size == before.size) return false
        writeAll(after)
        return true
    }

    /**
     * Find events at a venue that overlap the [start, end) window.
     * Overlap rule: event.start < end && event.end > start
     */
    fun findByVenueBetween(
        venueId: String,
        start: Instant,
        end: Instant
    ): List<Event> {
        require(!end.isBefore(start)) { "end must be ≥ start" }
        return readAll()
            .asSequence()
            .filter { it.venue.id == venueId }
            .filter { it.start.isBefore(end) && it.end.isAfter(start) }
            .sortedBy { it.start }
            .toList()
    }
}
