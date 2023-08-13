package com.rps.app.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface SpelareRep extends JpaRepository<SpelareObjekt, UUID> {
}


