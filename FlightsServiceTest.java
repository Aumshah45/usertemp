package com.test;

import com.dao.FlightsDAOImpl;
import com.entity.FlightSeatAvailablity;
import com.entity.Flights;
import com.entity.Flights.flight_status;
import com.repo.FlightSeatAvailablityRepo;
import com.repo.FlightsRepo;
import com.service.FlightsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FlightsServiceTest {

    @Mock
    private FlightsDAOImpl flightsDAOImpl;

    @Mock
    private FlightsRepo flightsRepo;

    @Mock
    private FlightSeatAvailablityRepo flightSeatAvailablityRepo;

    @InjectMocks
    private FlightsService flightsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewFlight() {
        Flights flight = new Flights();
        when(flightsDAOImpl.createFlight(any(Flights.class))).thenReturn(flight);

        Flights result = flightsService.createNewFlight(flight);

        assertEquals(flight, result);
        verify(flightsDAOImpl).createFlight(any(Flights.class));
    }

    @Test
    public void testDisplayFlights() {
        List<Flights> flights = Collections.emptyList();
        when(flightsDAOImpl.loadFlights()).thenReturn(flights);

        List<Flights> result = flightsService.displayFlights();

        assertEquals(flights, result);
        verify(flightsDAOImpl).loadFlights();
    }

    @Test
    public void testUpdateFlightListing() {
        Flights flight = new Flights();
        when(flightsDAOImpl.updateFlightListing(anyInt(), any(Boolean.class))).thenReturn(flight);

        Flights result = flightsService.updateFlightListing(1, true);

        assertEquals(flight, result);
        verify(flightsDAOImpl).updateFlightListing(anyInt(), any(Boolean.class));
    }

    @Test
    public void testUpdateFlightStatus() {
        Flights flight = new Flights();
        when(flightsDAOImpl.updateFlightStatus(anyInt(), any(flight_status.class))).thenReturn(flight);

        Flights result = flightsService.updateFlightStatus(1, flight_status.ON_TIME);

        assertEquals(flight, result);
        verify(flightsDAOImpl).updateFlightStatus(anyInt(), any(flight_status.class));
    }

    @Test
    public void testDeleteFlight() {
        doNothing().when(flightsDAOImpl).deleteFlightByID(anyInt());

        flightsService.deleteFlight(1);

        verify(flightsDAOImpl).deleteFlightByID(anyInt());
    }

    @Test
    public void testSearchFlightByID() {
        Flights flight = new Flights();
        when(flightsDAOImpl.searchByID(anyInt())).thenReturn(flight);

        Flights result = flightsService.searchFlightByID(1);

        assertEquals(flight, result);
        verify(flightsDAOImpl).searchByID(anyInt());
    }

    @Test
    public void testSearchFlight() {
        List<Flights> flights = Collections.emptyList();
        when(flightsDAOImpl.searchFlight(anyString(), anyString(), any(LocalDate.class))).thenReturn(flights);

        List<Flights> result = flightsService.searchFlight("dest", "source", LocalDate.now());

        assertEquals(flights, result);
        verify(flightsDAOImpl).searchFlight(anyString(), anyString(), any(LocalDate.class));
    }

    @Test
    public void testLoadFlightsByAirlineCode() {
        List<Flights> flights = Collections.emptyList();
        when(flightsDAOImpl.loadFlightsByAirlineCode(anyString())).thenReturn(flights);

        List<Flights> result = flightsService.loadFlightsByAirlineCode("airline");

        assertEquals(flights, result);
        verify(flightsDAOImpl).loadFlightsByAirlineCode(anyString());
    }

 @Test
public void testCreateSeatAvailablity() {
    FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
    Flights flight = new Flights();

    when(flightsRepo.getById(1)).thenReturn(flight);
   
    when(flightSeatAvailablityRepo.save(any(FlightSeatAvailablity.class))).thenAnswer(invocation -> {
        FlightSeatAvailablity savedEntity = invocation.getArgument(0);
        return savedEntity;  
    });
    
    flightsService.createSeatAvailablity(1, seatAvailablity);
   
    verify(flightSeatAvailablityRepo, times(1)).save(any(FlightSeatAvailablity.class));
}

    @Test
    public void testCreateFlightAndSeatAvailablity() {
        Flights flight = new Flights();
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        when(flightsRepo.save(any(Flights.class))).thenReturn(flight);
        when(flightSeatAvailablityRepo.save(any(FlightSeatAvailablity.class))).thenReturn(seatAvailablity);

        Flights result = flightsService.createFlightAndSeatAvailablity(flight);

        assertEquals(flight, result);
        verify(flightsRepo).save(any(Flights.class));
        verify(flightSeatAvailablityRepo).save(any(FlightSeatAvailablity.class));
    }

    @Test
    public void testUpdateSeatCount() {
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        when(flightSeatAvailablityRepo.getByFlight_id(anyInt())).thenReturn(seatAvailablity);
        when(flightSeatAvailablityRepo.save(any(FlightSeatAvailablity.class))).thenReturn(seatAvailablity);

        FlightSeatAvailablity result = flightsService.updateSeatCount(1, "economy");

        assertEquals(seatAvailablity, result);
        verify(flightSeatAvailablityRepo).getByFlight_id(anyInt());
        verify(flightSeatAvailablityRepo).save(any(FlightSeatAvailablity.class));
    }

    @Test
    public void testUpdateCancelledSeatCount() {
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        when(flightSeatAvailablityRepo.getByFlight_id(anyInt())).thenReturn(seatAvailablity);
        when(flightSeatAvailablityRepo.save(any(FlightSeatAvailablity.class))).thenReturn(seatAvailablity);

        FlightSeatAvailablity result = flightsService.updateCancelledSeatCount(1, "economy");

        assertEquals(seatAvailablity, result);
        verify(flightSeatAvailablityRepo).getByFlight_id(anyInt());
        verify(flightSeatAvailablityRepo).save(any(FlightSeatAvailablity.class));
    }
}
