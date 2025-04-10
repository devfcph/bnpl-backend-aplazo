package com.fcph.challengeaplazo.dto;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {
    private Long clientId;

    private Double assignedCreditLine;
}
