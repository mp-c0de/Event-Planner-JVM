package mpcode.algo

import java.time.Instant
import java.util.{List => JList, ArrayList}
import mpcode.domain.{Event, Venue}
import scala.jdk.CollectionConverters.*

/**
 * Result of scheduling operation containing successfully scheduled events
 * and any events that could not be scheduled due to conflicts.
 */
case class ScheduleResult(
    scheduled: JList[Event],
    conflicts: JList[Event]
)

object SchedulerApi {

    /** Kotlin/Java-friendly facade over SlotFinderApi */
    def firstAvailableWindow(
                                existing: JList[Event],
                                venue: Venue,
                                desiredStart: Instant,
                                durationMinutes: Int
                            ): Array[Instant] = {
        new SlotFinderApi().findFirstAvailableSlot(existing, venue, desiredStart, durationMinutes)
    }

    /** Returns true if candidate overlaps any event at the same venue */
    def overlaps(existing: JList[Event], candidate: Event): Boolean = {
        val venueId = candidate.getVenue.getId
        val start   = candidate.getStart
        val end     = candidate.getEnd

        val it = existing.iterator()
        var clash = false
        while (it.hasNext && !clash) {
            val e = it.next()
            if (e.getVenue.getId == venueId) {
                // overlap if start < e.end && end > e.start
                if (start.isBefore(e.getEnd) && end.isAfter(e.getStart)) clash = true
            }
        }
        clash
    }

    /** Shifts to next available start time if there's overlap */
    def shiftToNextFreeStart(
        existing: JList[Event],
        venue: Venue,
        desiredStart: Instant,
        durationMinutes: Int
    ): Instant = {
        val slot = new SlotFinderApi().findFirstAvailableSlot(existing, venue, desiredStart, durationMinutes)
        slot(0)
    }

    /**
     * Generates a conflict-free schedule for a list of events across available venues.
     *
     * Algorithm: Greedy scheduling approach
     * 1. Sort events by start time (earliest first)
     * 2. For each event, check if it conflicts with already scheduled events at same venue
     * 3. If no conflict, add to scheduled list
     * 4. If conflict, add to conflicts list
     *
     * Time Complexity: O(n²) where n is number of events
     * - For each event (n), we check overlap with all previously scheduled events (up to n)
     *
     * Space Complexity: O(n) for storing scheduled and conflict lists
     *
     * @param events List of events to schedule (may have desired times and venues)
     * @param venues List of available venues (not used in basic greedy, but available for extensions)
     * @return ScheduleResult containing successfully scheduled events and conflicts
     */
    def generateSchedule(events: JList[Event], venues: JList[Venue]): ScheduleResult = {
        // Convert to Scala collections for easier manipulation
        val eventList = events.asScala.toList

        // Sort events by start time (greedy: schedule earliest events first)
        val sortedEvents = eventList.sortBy(e => e.getStart.toEpochMilli)

        // Accumulators for scheduled and conflicting events
        val scheduled = new ArrayList[Event]()
        val conflicts = new ArrayList[Event]()

        // Process each event
        for (event <- sortedEvents) {
            // Check if this event conflicts with any already scheduled event
            if (overlaps(scheduled, event)) {
                conflicts.add(event)
            } else {
                scheduled.add(event)
            }
        }

        ScheduleResult(scheduled, conflicts)
    }
}
