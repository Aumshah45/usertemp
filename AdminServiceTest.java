package com.test;

import com.model.Flights;
import com.model.Flights.flight_status;
import com.service.AdminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    @Test
    public void testLoadFlightByAirlineCode() {
        List<Flights> flights = new ArrayList<>();
        // Add mock flights if needed
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(Flights.class)).thenReturn(Flux.fromIterable(flights));

        List<Flights> result = adminService.loadFlightByAirlineCode("ABC123");

        assertEquals(flights, result);
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(anyString(), anyString());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToFlux(Flights.class);
    }

    @Test
    public void testCreateNewFlight() {
        Flights flight = new Flights();
        // Mock the flight object with necessary fields
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(any(Flights.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Flights.class)).thenReturn(Mono.just(flight));

        Flights result = adminService.createNewFlight(flight);

        assertEquals(flight, result);
        verify(webClient).post();
        verify(requestBodyUriSpec).uri(anyString());
        verify(requestBodyUriSpec).bodyValue(any(Flights.class));
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Flights.class);
    }

    @Test
    public void testUpdateFlightStatus() {
        Flights flight = new Flights();
        // Mock the flight object with necessary fields
        when(webClient.put()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), anyInt(), any(flight_status.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Flights.class)).thenReturn(Mono.just(flight));

        Flights result = adminService.updateFlightStatus(1, flight_status.ON_TIME);

        assertEquals(flight, result);
        verify(webClient).put();
        verify(requestBodyUriSpec).uri(anyString(), anyInt(), any(flight_status.class));
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Flights.class);
    }

    @Test
    public void testDeleteFlight() {
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyInt())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        adminService.deleteFlight(1);

        verify(webClient).delete();
        verify(requestHeadersUriSpec).uri(anyString(), anyInt());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Void.class);
    }

    @Test
    public void testSearchByID() {
        Flights flight = new Flights();
        // Mock the flight object with necessary fields
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyInt())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Flights.class)).thenReturn(Mono.just(flight));

        Flights result = adminService.searchByID(1);

        assertEquals(flight, result);
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(anyString(), anyInt());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(Flights.class);
    }
}
