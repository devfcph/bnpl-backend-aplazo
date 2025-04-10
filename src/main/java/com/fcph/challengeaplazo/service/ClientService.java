package com.fcph.challengeaplazo.service;

import com.fcph.challengeaplazo.dto.ClientRequest;
import com.fcph.challengeaplazo.dto.ClientResponse;
import com.fcph.challengeaplazo.entity.Client;
import com.fcph.challengeaplazo.exception.BusinessRuleException;
import com.fcph.challengeaplazo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fcph.challengeaplazo.util.BusinessRules;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientResponse registerClient(ClientRequest clientRequest) {
        LocalDate birthDate = LocalDate.parse(clientRequest.getBirthDate());
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        double credit = BusinessRules.assignCreditLimitByAge(age);

        Client client = Client.builder()
                .name(clientRequest.getName())
                .birthDate(birthDate)
                .creditLine(credit)
                .build();

        client = clientRepository.save(client);

        return ClientResponse.builder()
                .clientId(client.getId())
                .assignedCreditLine(client.getCreditLine())
                .build();

    }
}
