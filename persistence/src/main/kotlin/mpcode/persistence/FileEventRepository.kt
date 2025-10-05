package mpcode.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mpcode.domain.Event
import mpcode.domain.EventId
import java.io.File
import java.nio.file.Path

class FileEventRepository(dataDir: Path) : EventRepository {
    private val mapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())

    private val file: File = dataDir.resolve("events.json").toFile()

    init {
        file.parentFile?.mkdirs()
        if (!file.exists()) {
            file.writeText("[]")
        }
    }

    override fun save(event: Event) {
        val all = findAll().toMutableList()
        all.add(event)
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, all)
    }

    override fun findAll(): List<Event> {
        if (!file.exists()) return emptyList()
        val typeRef = object : TypeReference<List<Event>>() {}
        return mapper.readValue(file, typeRef)
    }

    override fun findById(id: EventId): Event? =
        findAll().find { it.id == id }
}
