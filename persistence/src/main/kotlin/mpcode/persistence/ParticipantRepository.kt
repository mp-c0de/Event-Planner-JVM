package mpcode.persistence

import mpcode.domain.Participant

interface ParticipantRepository {
    fun save(participant: Participant)
    fun findAll(): List<Participant>
    fun findById(id: String): Participant?
    fun update(participant: Participant): Boolean
    fun deleteById(id: String): Boolean
}
