package com.test;
import com.controller.BookingController;
import com.entity.Booking;
import com.entity.Passenger;
import com.service.BookingService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    public void testCreateBooking() throws Exception {
        Booking booking = new Booking();  // Mock the booking object as needed

        doNothing().when(bookingService).createBooking(any(Booking.class));

        mockMvc.perform(post("/booking/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"status\": \"PENDING\"}")) // Add valid JSON for Booking
                .andExpect(status().isOk());

        verify(bookingService).createBooking(any(Booking.class));
    }

    @Test
    public void testUpdateStatus() throws Exception {
        doNothing().when(bookingService).updateStatus(anyLong(), any(Booking.BookingStatus.class));

        mockMvc.perform(put("/booking/updateStatus/1/PENDING"))
                .andExpect(status().isOk());

        verify(bookingService).updateStatus(anyLong(), any(Booking.BookingStatus.class));
    }

    @Test
    public void testListBookingByUserId() throws Exception {
        doNothing().when(bookingService).listBookingByUserId(anyLong());

        mockMvc.perform(get("/booking/getByUser/1"))
                .andExpect(status().isOk());

        verify(bookingService).listBookingByUserId(anyLong());
    }

    @Test
    public void testListBookingByFlightId() throws Exception {
        doNothing().when(bookingService).listBookingByFlightId(anyLong());

        mockMvc.perform(get("/booking/getByFlight/1"))
                .andExpect(status().isOk());

        verify(bookingService).listBookingByFlightId(anyLong());
    }

    @Test
    public void testConfirmBookingAndAddPassengers() throws Exception {
        List<Passenger> passengers = new ArrayList<>();  // Mock the passenger list

        doNothing().when(bookingService).confirmBookingAndAddPassengers(anyLong(), any(List.class));

        mockMvc.perform(put("/booking/1/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))  // Add valid JSON for passengers list
                .andExpect(status().isOk());

        verify(bookingService).confirmBookingAndAddPassengers(anyLong(), any(List.class));
    }
}
