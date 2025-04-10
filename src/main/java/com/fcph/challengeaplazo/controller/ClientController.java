package com.fcph.challengeaplazo.controller;

import com.fcph.challengeaplazo.dto.ClientRequest;
import com.fcph.challengeaplazo.dto.ClientResponse;
import com.fcph.challengeaplazo.service.ClientService;
import com.fcph.challengeaplazo.statics.ClienStatics;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> register(@Valid @RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientService.registerClient(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientResponse);
    }
}
