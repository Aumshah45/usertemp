package com.test;

import com.dao.BookingDao;
import com.entity.Booking;
import com.entity.Booking.BookingStatus;
import com.entity.Passenger;
import com.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class BookingTestService {

    @Mock
    private BookingDao bookingDao;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateBooking() {
        Booking booking = new Booking();  // Create a mock Booking object
        when(bookingDao.addBooking(any(Booking.class))).thenReturn(booking);

        bookingService.createBooking(booking);

        verify(bookingDao).addBooking(any(Booking.class));
    }

    @Test
    public void testUpdateStatus() {
        Booking booking = new Booking();  // Mock a Booking object
        when(bookingDao.updatestatus(anyLong(), any(BookingStatus.class))).thenReturn(booking);

        bookingService.updateStatus(1L, BookingStatus.PENDING);

        verify(bookingDao).updatestatus(anyLong(), any(BookingStatus.class));
    }

    @Test
    public void testListBookingByUserId() {
        when(bookingDao.listBookingByUserId(anyLong())).thenReturn(List.of());

        bookingService.listBookingByUserId(1L);

        verify(bookingDao).listBookingByUserId(anyLong());
    }

    @Test
    public void testListBookingByFlightId() {
        when(bookingDao.listBookingByFlightId(anyLong())).thenReturn(List.of());

        bookingService.listBookingByFlightId(1L);

        verify(bookingDao).listBookingByFlightId(anyLong());
    }

    @Test
    public void testConfirmBookingAndAddPassengers() {
        List<Passenger> passengers = mock(List.class);  // Mock passenger list

        doNothing().when(bookingDao).confirmBookingAndAddPassengers(anyLong(), any(List.class));

        bookingService.confirmBookingAndAddPassengers(1L, passengers);

        verify(bookingDao).confirmBookingAndAddPassengers(anyLong(), any(List.class));
    }
}
