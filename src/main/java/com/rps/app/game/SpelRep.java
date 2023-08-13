package com.rps.app.game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface SpelRep extends JpaRepository<SpelObjekt, UUID> {
    List<SpelObjekt> findByStatus(Position status);
}
