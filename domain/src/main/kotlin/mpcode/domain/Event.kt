package mpcode.domain

import java.time.Instant

@JvmInline
value class EventId(val value: String)

data class Venue(
    val id: String,
    val name: String,
    val capacity: Int
)

data class Event(
    val id: EventId,
    val title: String,
    val venue: Venue,
    val start: Instant,
    val end: Instant,
    val capacity: Int
)

data class Participant(
    val id: String,
    val name: String,
    val email: String
)

data class Registration(
    val eventId: EventId,
    val participantId: String
)
