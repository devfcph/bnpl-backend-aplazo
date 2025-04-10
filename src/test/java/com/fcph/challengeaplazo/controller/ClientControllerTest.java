package com.fcph.challengeaplazo.controller;

import com.fcph.challengeaplazo.ChallengeAplazoApplication;
import com.fcph.challengeaplazo.dto.ClientRequest;
import com.fcph.challengeaplazo.dto.ClientResponse;
import com.fcph.challengeaplazo.exception.BusinessRuleException;
import com.fcph.challengeaplazo.exception.GlobalExceptionHandler;
import com.fcph.challengeaplazo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(ClientController.class)
@SpringBootTest(classes = ChallengeAplazoApplication.class)
public class ClientControllerTest {

    private ClientService clientService;
    private ClientController clientController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        clientService = Mockito.mock(ClientService.class);
        clientController = new ClientController(clientService);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(clientController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void shouldRegisterClientSuccessfully() throws Exception {
        ClientRequest request = new ClientRequest("Carlos Pérez", "1999-01-01");

        ClientResponse mockResponse = ClientResponse.builder()
                .clientId(1L)
                .assignedCreditLine(5000.0)
                .build();

        when(clientService.registerClient(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.assignedCreditLine").value(5000.0));
    }

    @Test
    public void shouldReturnBadRequestForBusinessRuleException() throws Exception {
        // Datos de prueba
        ClientRequest request = new ClientRequest("Pepe", "2010-01-01");

        when(clientService.registerClient(any()))
                .thenThrow(new BusinessRuleException("Client not eligible due to age restrictions"));

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BUSINESS_RULE_VIOLATION"))
                .andExpect(jsonPath("$.message").value("Client not eligible due to age restrictions"));
    }

    @Test
    public void shouldReturnBadRequestForValidationError() throws Exception {
        ClientRequest request = new ClientRequest("", "");

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").exists());
    }
}
