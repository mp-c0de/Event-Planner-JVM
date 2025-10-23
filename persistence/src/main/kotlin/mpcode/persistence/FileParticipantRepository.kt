package mpcode.persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import mpcode.domain.Participant
import java.io.File
import java.nio.file.Path

class FileParticipantRepository(dataDir: Path) : ParticipantRepository {
    private val mapper = ObjectMapper()
        .registerKotlinModule()

    private val file: File = dataDir.resolve("participants.json").toFile()

    init {
        file.parentFile?.mkdirs()
        if (!file.exists()) file.writeText("[]")
    }

    private fun writeAll(participants: List<Participant>) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, participants)
        } catch (e: Exception) {
            println("ERROR participants.json write: ${e.message}")
            throw e
        }
    }

    private fun readAll(): List<Participant> {
        if (!file.exists()) return emptyList()
        val typeRef = object : TypeReference<List<Participant>>() {}
        return try {
            mapper.readValue(file, typeRef)
        } catch (e: Exception) {
            println("ERROR participants.json read: ${e.message}")
            emptyList()
        }
    }

    override fun save(participant: Participant) {
        val all = readAll().toMutableList()
        all.add(participant)
        writeAll(all)
    }

    override fun findAll(): List<Participant> = readAll()

    override fun findById(id: String): Participant? =
        readAll().find { it.id == id }

    override fun update(participant: Participant): Boolean {
        val all = readAll().toMutableList()
        val idx = all.indexOfFirst { it.id == participant.id }
        if (idx < 0) return false
        all[idx] = participant
        writeAll(all)
        return true
    }

    override fun deleteById(id: String): Boolean {
        val before = readAll()
        val after = before.filterNot { it.id == id }
        if (after.size == before.size) return false
        writeAll(after)
        return true
    }
}
