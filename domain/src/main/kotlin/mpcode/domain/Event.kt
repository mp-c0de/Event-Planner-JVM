package mpcode.domain

import java.time.Instant

typealias EventId = String  // simpler for JSON + Scala interop

data class Venue(
    val id: String,
    val name: String,
    val location: String,
    val capacity: Int
) {
    init {
        require(id.isNotBlank()) { "Venue ID cannot be blank" }
        require(name.isNotBlank()) { "Venue name cannot be blank" }
        require(location.isNotBlank()) { "Venue location cannot be blank" }
        require(capacity > 0) { "Venue capacity must be positive, got $capacity" }
    }
}

data class Event(
    val id: EventId,
    val title: String,
    val venue: Venue,
    val start: Instant,
    val end: Instant,
    val capacity: Int
) {
    init {
        require(id.isNotBlank()) { "Event ID cannot be blank" }
        require(title.isNotBlank()) { "Event title cannot be blank" }
        require(start.isBefore(end)) { "Event start must be before end: start=$start, end=$end" }
        require(capacity > 0) { "Event capacity must be positive, got $capacity" }
        require(capacity <= venue.capacity) { "Event capacity ($capacity) cannot exceed venue capacity (${venue.capacity})" }
    }
}

data class Participant(
    val id: String,
    val name: String,
    val email: String
) {
    init {
        require(id.isNotBlank()) { "Participant ID cannot be blank" }
        require(name.isNotBlank()) { "Participant name cannot be blank" }
        require(email.isNotBlank()) { "Participant email cannot be blank" }
        require(email.contains("@")) { "Participant email must be valid" }
    }
}

data class Registration(
    val eventId: EventId,
    val participantId: String
) {
    init {
        require(eventId.isNotBlank()) { "Event ID cannot be blank" }
        require(participantId.isNotBlank()) { "Participant ID cannot be blank" }
    }
}
