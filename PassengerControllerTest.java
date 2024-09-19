package com.test;

import com.controller.PassengerController;
import com.entity.Passenger;
import com.service.PassengerService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PassengerControllerTest {

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerController passengerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();
    }

    @Test
    public void testAddPassenger() throws Exception {
        List<Passenger> passengers = new ArrayList<>();  // Mock a list of passengers

        doNothing().when(passengerService).addPassenger(anyLong(), any(List.class));

        mockMvc.perform(post("/passenger/addPassengers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))  // Add valid JSON for passengers list
                .andExpect(status().isOk());

        verify(passengerService).addPassenger(anyLong(), any(List.class));
    }

    @Test
    public void testGetPassengersByBookingId() throws Exception {
        doNothing().when(passengerService).getPassengersByBookingId(anyLong());

        mockMvc.perform(get("/passenger/getPassengersByBookingId/1"))
                .andExpect(status().isOk());

        verify(passengerService).getPassengersByBookingId(anyLong());
    }
}
