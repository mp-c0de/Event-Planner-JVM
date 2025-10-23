package mpcode.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mpcode.domain.EventId
import mpcode.domain.Registration
import java.io.File
import java.nio.file.Path

class FileRegistrationRepository(dataDir: Path) : RegistrationRepository {
    private val mapper = ObjectMapper()
        .registerKotlinModule()

    private val file: File = dataDir.resolve("registrations.json").toFile()

    init {
        file.parentFile?.mkdirs()
        if (!file.exists()) file.writeText("[]")
    }

    private fun writeAll(registrations: List<Registration>) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, registrations)
        } catch (e: Exception) {
            println("ERROR registrations.json write: ${e.message}")
            throw e
        }
    }

    private fun readAll(): List<Registration> {
        if (!file.exists()) return emptyList()
        val typeRef = object : TypeReference<List<Registration>>() {}
        return try {
            mapper.readValue(file, typeRef)
        } catch (e: Exception) {
            println("ERROR registrations.json read: ${e.message}")
            emptyList()
        }
    }

    override fun save(registration: Registration) {
        val all = readAll().toMutableList()
        all.add(registration)
        writeAll(all)
    }

    override fun findAll(): List<Registration> = readAll()

    override fun findByEventId(eventId: EventId): List<Registration> =
        readAll().filter { it.eventId == eventId }

    override fun findByParticipantId(participantId: String): List<Registration> =
        readAll().filter { it.participantId == participantId }

    override fun deleteByEventAndParticipant(eventId: EventId, participantId: String): Boolean {
        val before = readAll()
        val after = before.filterNot { it.eventId == eventId && it.participantId == participantId }
        if (after.size == before.size) return false
        writeAll(after)
        return true
    }

    override fun isParticipantRegistered(eventId: EventId, participantId: String): Boolean =
        readAll().any { it.eventId == eventId && it.participantId == participantId }
}
