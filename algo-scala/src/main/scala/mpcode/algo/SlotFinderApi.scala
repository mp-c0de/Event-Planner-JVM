package mpcode.algo

// Import Statements
import java.time.{Duration, Instant}
import java.util as ju
import mpcode.domain.{Event, Venue}
import scala.jdk.CollectionConverters.*

/** Finds first non-overlapping slot at the same venue. */
class SlotFinderApi:

  /** Returns [start, end] where durationMinutes fits without overlap. */
  def findFirstAvailableSlot(
      existing: ju.List[Event],
      venue: Venue,
      desiredStart: Instant,
      durationMinutes: Int
  ): Array[Instant] =
    val dur = Duration.ofMinutes(durationMinutes.toLong)

    // Java List -> Scala, same venue, sort by Kotlin getters (Java-style)
    val sameVenue = existing.asScala
      .filter(e => e.getVenue().getId() == venue.getId())
      .toList
      .sortBy(e => e.getStart().toEpochMilli)

    var candidate = desiredStart
    for e <- sameVenue do
      val candidateEnd = candidate.plus(dur)
      val overlap = candidate.isBefore(e.getEnd()) && candidateEnd.isAfter(e.getStart())
      if overlap && e.getEnd().isAfter(candidate) then
        candidate = e.getEnd()

    Array(candidate, candidate.plus(dur))
