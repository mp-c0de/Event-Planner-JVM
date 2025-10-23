package mpcode.app

import mpcode.persistence.FileEventRepository
import mpcode.persistence.FileParticipantRepository
import mpcode.persistence.FileRegistrationRepository
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

    val participantRepository: FileParticipantRepository by lazy {
        FileParticipantRepository(dataPath)
    }

    val registrationRepository: FileRegistrationRepository by lazy {
        FileRegistrationRepository(dataPath)
    }
}

