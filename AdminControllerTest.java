package com.test;

import com.controller.AdminController;
import com.model.Flights;
import com.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest {

    @Mock
    private AdminService service;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testLoadAirlinesFlight() throws Exception {
        List<Flights> flights = new ArrayList<>();
        // Populate flights with mock data if needed

        when(service.loadFlightByAirlineCode(anyString())).thenReturn(flights);

        mockMvc.perform(get("/admin/loadairlinesflight/ABC123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());

        verify(service).loadFlightByAirlineCode(anyString());
    }

    @Test
    public void testAddNewFlight() throws Exception {
        Flights flight = new Flights();  // Mock flight object with necessary fields

        when(service.createNewFlight(any(Flights.class))).thenReturn(flight);

        mockMvc.perform(post("/admin/addnewflight")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"flightID\": 1, \"status\": \"ON_TIME\"}")) // Provide valid JSON for Flights
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightID").value(1));

        verify(service).createNewFlight(any(Flights.class));
    }

    @Test
    public void testUpdateFlightDetails() throws Exception {
        Flights flight = new Flights();  // Mock flight object with necessary fields
        when(service.updateFlightStatus(anyInt(), any(Flights.flight_status.class))).thenReturn(flight);

        mockMvc.perform(put("/admin/updateflightstatus/1/ON_TIME"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightID").value(1));

        verify(service).updateFlightStatus(anyInt(), any(Flights.flight_status.class));
    }

    @Test
    public void testSearchByID() throws Exception {
        Flights flight = new Flights();  // Mock flight object with necessary fields
        when(service.searchByID(anyInt())).thenReturn(flight);

        mockMvc.perform(get("/admin/searchbyid/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flightID").value(1));

        verify(service).searchByID(anyInt());
    }

    @Test
    public void testDeleteFlightByID() throws Exception {
        doNothing().when(service).deleteFlight(anyInt());

        mockMvc.perform(delete("/admin/deleteflightbyid/1"))
                .andExpect(status().isOk());

        verify(service).deleteFlight(anyInt());
    }
}
