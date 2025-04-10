package com.fcph.challengeaplazo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    @NotBlank
    private String name;

    @NotNull
    private String birthDate;
}
