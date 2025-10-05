package mpcode.persistence

import mpcode.domain.Event
import mpcode.domain.EventId

interface EventRepository {
    fun save(event: Event)
    fun findAll(): List<Event>
    fun findById(id: EventId): Event?
}
