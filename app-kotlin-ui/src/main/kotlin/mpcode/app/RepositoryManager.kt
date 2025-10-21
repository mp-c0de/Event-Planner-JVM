package mpcode.app

import mpcode.persistence.FileEventRepository
import java.nio.file.Paths

/**
 * Centralized repository management to avoid duplicate instances.
 * Provides singleton access to repositories across the application.
 */
object RepositoryManager {
    private val dataPath = Paths.get("data")

    val eventRepository: FileEventRepository by lazy {
        FileEventRepository(dataPath)
    }
}
