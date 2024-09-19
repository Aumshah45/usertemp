package com.test;

import com.dao.PassengerDao;
import com.entity.Passenger;
import com.service.PassengerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class PassengerServiceTest {

    @Mock
    private PassengerDao passengerDao;

    @InjectMocks
    private PassengerService passengerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPassenger() {
        List<Passenger> passengers = mock(List.class);  // Mock the passenger list
        when(passengerDao.addPassengers(anyLong(), any(List.class))).thenReturn(passengers);

        passengerService.addPassenger(1L, passengers);

        verify(passengerDao).addPassengers(anyLong(), any(List.class));
    }

    @Test
    public void testGetPassengersByBookingId() {
        List<Passenger> passengers = mock(List.class);  // Mock the passenger list
        when(passengerDao.getPassengersByBookingId(anyLong())).thenReturn(passengers);

        passengerService.getPassengersByBookingId(1L);

        verify(passengerDao).getPassengersByBookingId(anyLong());
    }
}
