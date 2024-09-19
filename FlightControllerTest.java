package com.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.entity.FlightSeatAvailablity;
import com.entity.Flights;
import com.entity.Flights.flight_status;
import com.service.FlightsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FlightsService flightsService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    public void testAddNewFlight() throws Exception {
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");

        when(flightsService.createNewFlight(any(Flights.class))).thenReturn(flight);

        mockMvc.perform(post("/flights/addflight")
                .contentType("application/json")
                .content("{\"source_airport\": \"JFK\", \"destination_airport\": \"LAX\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination_airport").value("LAX"));
    }

    @Test
    public void testShowAllFlights() throws Exception {
        List<Flights> flightsList = new ArrayList<>();
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");
        flightsList.add(flight);

        when(flightsService.displayFlights()).thenReturn(flightsList);

        mockMvc.perform(get("/flights/showallflights"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination_airport").value("LAX"));
    }

    @Test
    public void testUpdateFlightDetails() throws Exception {
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");

        when(flightsService.updateFlightListing(anyInt(), anyBoolean())).thenReturn(flight);

        mockMvc.perform(put("/flights/updateflightlisting/1/false"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination_airport").value("LAX"));
    }

    @Test
    public void testUpdateFlightStatus() throws Exception {
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");

        when(flightsService.updateFlightStatus(anyInt(), any(flight_status.class))).thenReturn(flight);

        mockMvc.perform(put("/flights/updateflightstatus/1/ON_TIME"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination_airport").value("LAX"));
    }

    @Test
    public void testDeleteFlight() throws Exception {
        doNothing().when(flightsService).deleteFlight(anyInt());

        mockMvc.perform(delete("/flights/deleteflight/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchFlightByID() throws Exception {
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");

        when(flightsService.searchFlightByID(anyInt())).thenReturn(flight);

        mockMvc.perform(get("/flights/searchbyid/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination_airport").value("LAX"));
    }

    @Test
    public void testSearchFlights() throws Exception {
        List<Flights> flightsList = new ArrayList<>();
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");
        flightsList.add(flight);

        when(flightsService.searchFlight(anyString(), anyString(), any(LocalDate.class))).thenReturn(flightsList);

        mockMvc.perform(get("/flights/searchflight/JFK/LAX/2024-09-19"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination_airport").value("LAX"));
    }

    @Test
    public void testSearchAirlineFlights() throws Exception {
        List<Flights> flightsList = new ArrayList<>();
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");
        flightsList.add(flight);

        when(flightsService.loadFlightsByAirlineCode(anyString())).thenReturn(flightsList);

        mockMvc.perform(get("/flights/searchairlineflights/XYZ"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination_airport").value("LAX"));
    }

    @Test
    public void testCreateNewSeatAvailablity() throws Exception {
        doNothing().when(flightsService).createSeatAvailablity(anyInt(), any(FlightSeatAvailablity.class));

        mockMvc.perform(post("/flights/seatavailablity/1")
                .contentType("application/json")
                .content("{\"economy_seats_left\": 10, \"premium_economy_seats_left\": 5, \"business_seats_left\": 2}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateFlightAndSeatAvailablity() throws Exception {
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");

        when(flightsService.createFlightAndSeatAvailablity(any(Flights.class))).thenReturn(flight);

        mockMvc.perform(post("/flights/createflight")
                .contentType("application/json")
                .content("{\"source_airport\": \"JFK\", \"destination_airport\": \"LAX\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination_airport").value("LAX"));
    }

    @Test
    public void testSearchEconomyFlights() throws Exception {
        List<Flights> flightsList = new ArrayList<>();
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");
        flightsList.add(flight);

        when(flightsService.searchEconomyFlights(anyString(), anyString(), any(LocalDate.class))).thenReturn(flightsList);

        mockMvc.perform(get("/flights/searcheconomyflight/JFK/LAX/2024-09-19"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination_airport").value("LAX"));
    }

       @Test
    public void testSearchPremiumEconomyFlights() throws Exception {
        List<Flights> flightsList = new ArrayList<>();
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");
        flightsList.add(flight);

        when(flightsService.searchPremiumEconomyFlights(anyString(), anyString(), any(LocalDate.class))).thenReturn(flightsList);

        mockMvc.perform(get("/flights/searchpremiumeconomyflight/JFK/LAX/2024-09-19"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination_airport").value("LAX"));
    }

    @Test
    public void testSearchBusinessFlights() throws Exception {
        List<Flights> flightsList = new ArrayList<>();
        Flights flight = new Flights();
        flight.setFlightID(1);
        flight.setSource_airport("JFK");
        flight.setDestination_airport("LAX");
        flightsList.add(flight);

        when(flightsService.searchBusinessFlights(anyString(), anyString(), any(LocalDate.class))).thenReturn(flightsList);

        mockMvc.perform(get("/flights/searchbusinessflight/JFK/LAX/2024-09-19"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].source_airport").value("JFK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination_airport").value("LAX"));
    }

    @Test
    public void testUpdateSeatCount() throws Exception {
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        seatAvailablity.setEconomySeatsLeft(10);
        seatAvailablity.setPremiumEconomySeatsLeft(5);
        seatAvailablity.setBusinessSeatsLeft(2);

        when(flightsService.updateSeatCount(anyInt(), anyString())).thenReturn(seatAvailablity);

        mockMvc.perform(put("/flights/updateseatcount/1/economy"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.economySeatsLeft").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.premiumEconomySeatsLeft").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessSeatsLeft").value(2));
    }

    @Test
    public void testUpdateCancelledSeatCount() throws Exception {
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        seatAvailablity.setEconomySeatsLeft(10);
        seatAvailablity.setPremiumEconomySeatsLeft(5);
        seatAvailablity.setBusinessSeatsLeft(2);

        when(flightsService.updateCancelledSeatCount(anyInt(), anyString())).thenReturn(seatAvailablity);

        mockMvc.perform(put("/flights/updatecancelledseatcount/1/economy"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.economySeatsLeft").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.premiumEconomySeatsLeft").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.businessSeatsLeft").value(2));
    }
}
