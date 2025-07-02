package com.gridnine.testing.filter;
import com.gridnine.testing.model.Flight;

import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class FlightFilterTest {
    @Test
    void testDepartureBeforeNowFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight pastFlight = new Flight(Arrays.asList(
                new Segment(now.minusDays(1), now.plusHours(2))));
        Flight futureFlight = new Flight(Arrays.asList(
                new Segment(now.plusDays(1), now.plusDays(1).plusHours(2))));

        List<Flight> flights = Arrays.asList(pastFlight, futureFlight);
        List<Flight> filtered = new DepartureBeforeNowFilter().filter(flights);

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(futureFlight));
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight invalidFlight = new Flight(Arrays.asList(
                new Segment(now, now.minusHours(1))));
        Flight validFlight = new Flight(Arrays.asList(
                new Segment(now, now.plusHours(1))));

        List<Flight> flights = Arrays.asList(invalidFlight, validFlight);
        List<Flight> filtered = new ArrivalBeforeDepartureFilter().filter(flights);

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(validFlight));
    }

    @Test
    void testGroundTimeExceedsTwoHoursFilter() {
        LocalDateTime now = LocalDateTime.now();
        // Перелёт с общим временем на земле 3 часа (1 + 2)
        Flight longGroundTimeFlight = new Flight(Arrays.asList(
                new Segment(now, now.plusHours(1)),
                new Segment(now.plusHours(2), now.plusHours(3)),
                new Segment(now.plusHours(5), now.plusHours(6))));

        // Перелёт с общим временем на земле 1 час
        Flight shortGroundTimeFlight = new Flight(Arrays.asList(
                new Segment(now, now.plusHours(1)),
                new Segment(now.plusHours(2), now.plusHours(3))));

        List<Flight> flights = Arrays.asList(longGroundTimeFlight, shortGroundTimeFlight);
        List<Flight> filtered = new GroundTimeExceedsTwoHoursFilter().filter(flights);

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(shortGroundTimeFlight));
    }

    @Test
    void testSingleSegmentFlightPassesGroundTimeFilter() {
        LocalDateTime now = LocalDateTime.now();
        Flight singleSegmentFlight = new Flight(Arrays.asList(
                new Segment(now, now.plusHours(2))));

        List<Flight> filtered = new GroundTimeExceedsTwoHoursFilter().filter(Arrays.asList(singleSegmentFlight));

        assertEquals(1, filtered.size());
        assertTrue(filtered.contains(singleSegmentFlight));
    }
}
