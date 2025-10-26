package mpcode.algo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import java.time.Instant
import mpcode.domain.{Event, Venue}
import java.util.{List => JList}

class SchedulerApiTest:

  @Test
  def schedulesNonConflictingEvents(): Unit =
    val venue = new Venue("v1", "Main Hall", "Building A", 100)

    // Two events at different times, same venue
    val e1 = new Event("e1", "Morning Workshop", venue,
      Instant.parse("2025-01-01T09:00:00Z"),
      Instant.parse("2025-01-01T11:00:00Z"), 50)

    val e2 = new Event("e2", "Afternoon Talk", venue,
      Instant.parse("2025-01-01T14:00:00Z"),
      Instant.parse("2025-01-01T16:00:00Z"), 50)

    val events = java.util.List.of(e1, e2)
    val venues = java.util.List.of(venue)

    val result = SchedulerApi.generateSchedule(events, venues)

    assertEquals(2, result.scheduled.size(), "Both events should be scheduled")
    assertEquals(0, result.conflicts.size(), "No conflicts expected")

  @Test
  def detectsConflictingEvents(): Unit =
    val venue = new Venue("v1", "Main Hall", "Building A", 100)

    // Two overlapping events at same venue
    val e1 = new Event("e1", "Workshop A", venue,
      Instant.parse("2025-01-01T10:00:00Z"),
      Instant.parse("2025-01-01T12:00:00Z"), 50)

    val e2 = new Event("e2", "Workshop B", venue,
      Instant.parse("2025-01-01T11:00:00Z"),
      Instant.parse("2025-01-01T13:00:00Z"), 50)

    val events = java.util.List.of(e1, e2)
    val venues = java.util.List.of(venue)

    val result = SchedulerApi.generateSchedule(events, venues)

    assertEquals(1, result.scheduled.size(), "Only first event should be scheduled")
    assertEquals(1, result.conflicts.size(), "Second event should conflict")
    assertEquals("e2", result.conflicts.get(0).getId, "Conflicting event should be e2")

  @Test
  def schedulesDifferentVenuesWithoutConflict(): Unit =
    val venue1 = new Venue("v1", "Hall A", "Building A", 100)
    val venue2 = new Venue("v2", "Hall B", "Building B", 100)

    // Same time, different venues - should both schedule
    val e1 = new Event("e1", "Event A", venue1,
      Instant.parse("2025-01-01T10:00:00Z"),
      Instant.parse("2025-01-01T12:00:00Z"), 50)

    val e2 = new Event("e2", "Event B", venue2,
      Instant.parse("2025-01-01T10:00:00Z"),
      Instant.parse("2025-01-01T12:00:00Z"), 50)

    val events = java.util.List.of(e1, e2)
    val venues = java.util.List.of(venue1, venue2)

    val result = SchedulerApi.generateSchedule(events, venues)

    assertEquals(2, result.scheduled.size(), "Both events at different venues should schedule")
    assertEquals(0, result.conflicts.size(), "No conflicts for different venues")

  @Test
  def schedulesInChronologicalOrder(): Unit =
    val venue = new Venue("v1", "Main Hall", "Building A", 100)

    // Create events in reverse chronological order
    val e2 = new Event("e2", "Later Event", venue,
      Instant.parse("2025-01-01T14:00:00Z"),
      Instant.parse("2025-01-01T16:00:00Z"), 50)

    val e1 = new Event("e1", "Earlier Event", venue,
      Instant.parse("2025-01-01T10:00:00Z"),
      Instant.parse("2025-01-01T12:00:00Z"), 50)

    val events = java.util.List.of(e2, e1) // Note: added in reverse order
    val venues = java.util.List.of(venue)

    val result = SchedulerApi.generateSchedule(events, venues)

    // Should schedule both in chronological order
    assertEquals(2, result.scheduled.size())
    assertEquals("e1", result.scheduled.get(0).getId, "Earlier event should be first")
    assertEquals("e2", result.scheduled.get(1).getId, "Later event should be second")
