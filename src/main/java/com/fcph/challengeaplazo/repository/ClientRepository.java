package com.fcph.challengeaplazo.repository;

import com.fcph.challengeaplazo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
