package mpcode.persistence

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.nio.file.Files
import mpcode.domain.*

class FileEventRepositoryTest {
    @Test
    fun `update and delete work`() {
        val tmp = Files.createTempDirectory("repo-test")
        val repo = FileEventRepository(tmp)
        val venue = Venue("v1", "Main", "Building A", 100)
        val e = Event("id1", "A", venue, java.time.Instant.now(), java.time.Instant.now().plusSeconds(3600), 10)
        repo.save(e)

        val updated = e.copy(title = "A+")
        repo.update(updated)
        assertEquals("A+", repo.findById("id1")?.title)

        assertTrue(repo.deleteById("id1") == true)
        assertNull(repo.findById("id1"))
    }
}
