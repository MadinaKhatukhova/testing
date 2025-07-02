package com.gridnine.testing;

import com.gridnine.testing.filter.ArrivalBeforeDepartureFilter;
import com.gridnine.testing.filter.DepartureBeforeNowFilter;
import com.gridnine.testing.filter.FlightFilterProcessor;
import com.gridnine.testing.filter.GroundTimeExceedsTwoHoursFilter;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;

import java.util.List;


public class Main {

	public static void main(String[] args) {
		List<Flight> flights = FlightBuilder.createFlights();

		// Вывод всех перелётов
		FlightFilterProcessor.displayFlights(flights, "All flights");

		// Фильтр 1: Исключить перелёты с вылетом до текущего момента времени
		FlightFilterProcessor processor1 = new FlightFilterProcessor(new DepartureBeforeNowFilter());
		List<Flight> filteredFlights1 = processor1.process(flights);
		FlightFilterProcessor.displayFlights(filteredFlights1, "Flights with departure after current time");

		// Фильтр 2: Исключить сегменты с датой прилёта раньше даты вылета
		FlightFilterProcessor processor2 = new FlightFilterProcessor(new ArrivalBeforeDepartureFilter());
		List<Flight> filteredFlights2 = processor2.process(flights);
		FlightFilterProcessor.displayFlights(filteredFlights2, "Flights with arrival after departure");

		// Фильтр 3: Исключить перелёты с общим временем на земле более 2 часов
		FlightFilterProcessor processor3 = new FlightFilterProcessor(new GroundTimeExceedsTwoHoursFilter());
		List<Flight> filteredFlights3 = processor3.process(flights);
		FlightFilterProcessor.displayFlights(filteredFlights3, "Flights with ground time less than 2 hours");
	}

}
