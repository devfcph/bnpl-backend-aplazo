package com.fcph.challengeaplazo.entity;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.Name;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private Double creditLine;
}
