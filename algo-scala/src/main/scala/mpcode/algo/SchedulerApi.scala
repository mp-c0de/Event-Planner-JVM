package mpcode.algo

import java.time.Instant
import java.util.{List => JList}
import mpcode.domain.{Event, Venue}

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
}
