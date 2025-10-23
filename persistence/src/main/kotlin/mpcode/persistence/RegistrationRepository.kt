package mpcode.persistence

import mpcode.domain.EventId
import mpcode.domain.Registration

interface RegistrationRepository {
    fun save(registration: Registration)
    fun findAll(): List<Registration>
    fun findByEventId(eventId: EventId): List<Registration>
    fun findByParticipantId(participantId: String): List<Registration>
    fun deleteByEventAndParticipant(eventId: EventId, participantId: String): Boolean
    fun isParticipantRegistered(eventId: EventId, participantId: String): Boolean
}
