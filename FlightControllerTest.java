package com.test;

import com.controller.FlightController;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Mock
    private FlightsService flightsService;

    @InjectMocks
    private FlightController flightController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    public void testAddNewFlight() throws Exception {
        Flights flight = new Flights();
        when(flightsService.createNewFlight(any(Flights.class))).thenReturn(flight);

        mockMvc.perform(post("/flights/addflight")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"source_airport\":\"source\",\"destination_airport\":\"destination\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("source"));

        verify(flightsService).createNewFlight(any(Flights.class));
    }

    @Test
    public void testShowAllFlights() throws Exception {
        List<Flights> flights = Collections.emptyList();
        when(flightsService.displayFlights()).thenReturn(flights);

        mockMvc.perform(get("/flights/showallflights"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(flightsService).displayFlights();
    }

    @Test
    public void testUpdateFlightDetails() throws Exception {
        Flights flight = new Flights();
        when(flightsService.updateFlightListing(anyInt(), anyBoolean())).thenReturn(flight);

        mockMvc.perform(put("/flights/updateflightlisting/1/true"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").doesNotExist());

        verify(flightsService).updateFlightListing(anyInt(), anyBoolean());
    }

    @Test
    public void testUpdateFlightStatus() throws Exception {
        Flights flight = new Flights();
        when(flightsService.updateFlightStatus(anyInt(), any(flight_status.class))).thenReturn(flight);

        mockMvc.perform(put("/flights/updateflightstatus/1/ON_TIME"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").doesNotExist());

        verify(flightsService).updateFlightStatus(anyInt(), any(flight_status.class));
    }

    @Test
    public void testDeleteFlight() throws Exception {
        doNothing().when(flightsService).deleteFlight(anyInt());

        mockMvc.perform(delete("/flights/deleteflight/1"))
                .andExpect(status().isOk());

        verify(flightsService).deleteFlight(anyInt());
    }

    @Test
    public void testSearchFlightByID() throws Exception {
        Flights flight = new Flights();
        when(flightsService.searchFlightByID(anyInt())).thenReturn(flight);

        mockMvc.perform(get("/flights/searchbyid/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").doesNotExist());

        verify(flightsService).searchFlightByID(anyInt());
    }

    @Test
    public void testSearchFlights() throws Exception {
        List<Flights> flights = Collections.emptyList();
        when(flightsService.searchFlight(anyString(), anyString(), any(LocalDate.class))).thenReturn(flights);

        mockMvc.perform(get("/flights/searchflight/source/destination/2024-09-20"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(flightsService).searchFlight(anyString(), anyString(), any(LocalDate.class));
    }

    @Test
    public void testSearchAirlineFlights() throws Exception {
        List<Flights> flights = Collections.emptyList();
        when(flightsService.loadFlightsByAirlineCode(anyString())).thenReturn(flights);

        mockMvc.perform(get("/flights/searchairlineflights/airline"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(flightsService).loadFlightsByAirlineCode(anyString());
    }

    @Test
    public void testCreateNewSeatAvailablity() throws Exception {
        doNothing().when(flightsService).createSeatAvailablity(anyInt(), any(FlightSeatAvailablity.class));

        mockMvc.perform(post("/flights/seatavailablity/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"economy_seats_left\":10, \"premium_economy_seats_left\":5, \"business_seats_left\":2}"))
                .andExpect(status().isOk());

        verify(flightsService).createSeatAvailablity(anyInt(), any(FlightSeatAvailablity.class));
    }

    @Test
    public void testCreateFlightAndSeatAvailablity() throws Exception {
        Flights flight = new Flights();
        when(flightsService.createFlightAndSeatAvailablity(any(Flights.class))).thenReturn(flight);

        mockMvc.perform(post("/flights/createflight")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"source_airport\":\"source\",\"destination_airport\":\"destination\"}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.source_airport").value("source"));

        verify(flightsService).createFlightAndSeatAvailablity(any(Flights.class));
    }

    @Test
    public void testSearchEconomyFlights() throws Exception {
        List<Flights> flights = Collections.emptyList();
        when(flightsService.searchEconomyFlights(anyString(), anyString(), any(LocalDate.class))).thenReturn(flights);

        mockMvc.perform(get("/flights/searcheconomyflight/source/destination/2024-09-20"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(flightsService).searchEconomyFlights(anyString(), anyString(), any(LocalDate.class));
    }

    @Test
    public void testSearchPremiumEconomyFlights() throws Exception {
        List<Flights> flights = Collections.emptyList();
        when(flightsService.searchPremiumEconomyFlights(anyString(), anyString(), any(LocalDate.class))).thenReturn(flights);

        mockMvc.perform(get("/flights/searchpremiumeconomyflight/source/destination/2024-09-20"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(flightsService).searchPremiumEconomyFlights(anyString(), anyString(), any(LocalDate.class));
    }

    @Test
    public void testSearchBusinessFlights() throws Exception {
        List<Flights> flights = Collections.emptyList();
        when(flightsService.searchBusinessFlights(anyString(), anyString(), any(LocalDate.class))).thenReturn(flights);

        mockMvc.perform(get("/flights/searchbusinessflight/source/destination/2024-09-20"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        verify(flightsService).searchBusinessFlights(anyString(), anyString(), any(LocalDate.class));
    }

    @Test
    public void testUpdateSeatCount() throws Exception {
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        when(flightsService.updateSeatCount(anyInt(), anyString())).thenReturn(seatAvailablity);

        mockMvc.perform(put("/flights/updateseatcount/1/economy"))
                .andExpect(status().isOk());

        verify(flightsService).updateSeatCount(anyInt(), anyString());
    }

    @Test
    public void testUpdateCancelledSeatCount() throws Exception {
        FlightSeatAvailablity seatAvailablity = new FlightSeatAvailablity();
        when(flightsService.updateCancelledSeatCount(anyInt(), anyString())).thenReturn(seatAvailablity);

        mockMvc.perform(put("/flights/updatecancelledseatcount/1/economy"))
                .andExpect(status().isOk());

        verify(flightsService).updateCancelledSeatCount(anyInt(), anyString());
    }
}
