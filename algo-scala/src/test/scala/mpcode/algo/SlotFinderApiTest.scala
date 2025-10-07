package mpcode.algo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions._
import java.time.Instant
import mpcode.domain.{Event, Venue}

class SlotFinderApiTest:

  @Test
  def movesStartAfterOverlap(): Unit =
    val api = new SlotFinderApi()
    val venue = new Venue("v1", "Main", 100)

    val e1Start = Instant.parse("2025-01-01T10:00:00Z")
    val e1End   = Instant.parse("2025-01-01T11:00:00Z")
    val existing = java.util.List.of(
      new Event("e1", "A", venue, e1Start, e1End, 50)
    )

    val desired = Instant.parse("2025-01-01T10:30:00Z")
    val slot = api.findFirstAvailableSlot(existing, venue, desired, 60)

    assertEquals(e1End, slot(0))                      // 11:00Z
    assertEquals(e1End.plusSeconds(3600), slot(1))    // 12:00Z
