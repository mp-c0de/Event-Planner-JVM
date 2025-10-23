package mpcode.persistence

import mpcode.domain.Event
import mpcode.domain.EventId
import java.time.Instant

interface EventRepository {
    fun save(event: Event)
    fun findAll(): List<Event>
    fun findById(id: EventId): Event?
    fun update(event: Event): Boolean
    fun deleteById(id: EventId): Boolean
    fun findByVenueBetween(venueId: String, start: Instant, end: Instant): List<Event>
}
