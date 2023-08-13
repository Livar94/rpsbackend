package com.rps.app.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class SpelareSevrices {

    private static SpelareRep playerRepository;


    @Autowired
    public SpelareSevrices(SpelareRep playerRepository) {
        SpelareSevrices.playerRepository = playerRepository;
    }





    public SpelareObjekt generateToken() {
        UUID uuid = UUID.randomUUID();
        System.out.println("Generated UUID: " + uuid);

        SpelareObjekt playerEntity = new SpelareObjekt();
        playerEntity.setPlayerId(uuid);

        try {
            playerEntity = playerRepository.save(playerEntity);
            System.out.println("UUID saved in the database: " + playerEntity.getPlayerId());
        } catch (Exception e) {
            System.out.println("Error saving the UUID in the database: " + e.getMessage());
        }

        return playerEntity;
    }



    public void setName(UUID playerId, UpdateraSpelareNamn requestDTO) {
        Optional<SpelareObjekt> player = playerRepository.findById(playerId);

        if (player.isPresent()) {
            SpelareObjekt playerEntity = player.get();
            playerEntity.setPlayerName(requestDTO.getName());
            playerRepository.save(playerEntity);


            Optional<SpelareObjekt> savedPlayer = playerRepository.findById(playerId);
            if (savedPlayer.isPresent()) {
                System.out.println("Player entity saved successfully with UUID: " + savedPlayer.get().getPlayerId());
            } else {
                System.out.println("Failed to save player entity with UUID: " + playerId);
            }

        } else {
            throw new RuntimeException("Token not found: " + playerId);
        }
    }


}