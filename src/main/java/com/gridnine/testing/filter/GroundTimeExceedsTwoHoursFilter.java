package com.gridnine.testing.filter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    if (segments.size() <= 1) {
                        return true;
                    }

                    long totalGroundTime = 0;
                    for (int i = 1; i < segments.size(); i++) {
                        LocalDateTime previousArrival = segments.get(i-1).getArrivalDate();
                        LocalDateTime currentDeparture = segments.get(i).getDepartureDate();
                        totalGroundTime += Duration.between(previousArrival, currentDeparture).toHours();
                    }

                    return totalGroundTime <= 2;
                })
                .collect(Collectors.toList());
    }
}
