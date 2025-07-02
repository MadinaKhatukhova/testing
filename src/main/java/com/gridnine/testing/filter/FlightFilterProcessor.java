package com.gridnine.testing.filter;
import com.gridnine.testing.model.Flight;
import java.util.List;

public class FlightFilterProcessor {
    private final FlightFilter filter;

    public FlightFilterProcessor(FlightFilter filter) {
        this.filter = filter;
    }

    public List<Flight> process(List<Flight> flights) {
        return filter.filter(flights);
    }

    public static void displayFlights(List<Flight> flights, String title) {
        System.out.println(title);
        System.out.println("--------------------------------------------------");
        if (flights.isEmpty()) {
            System.out.println("No flights found");
        } else {
            flights.forEach(System.out::println);
        }
        System.out.println("--------------------------------------------------\n");
    }
}
