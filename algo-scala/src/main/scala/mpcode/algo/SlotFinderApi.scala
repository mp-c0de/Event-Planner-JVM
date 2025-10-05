package mpcode.algo

import java.time.Instant
import java.util as ju
import mpcode.domain.{Event, Venue}

class SlotFinderApi:
  /**
   * Naive placeholder: returns [desiredStart, desiredStart + durationMinutes].
   * Replace with real conflict checks against `existing` and venue capacity.
   */
  def findFirstAvailableSlot(
      existing: ju.List[Event],
      venue: Venue,
      desiredStart: Instant,
      durationMinutes: Int
  ): Array[Instant] =
    Array(desiredStart, desiredStart.plusSeconds(durationMinutes.toLong * 60))
