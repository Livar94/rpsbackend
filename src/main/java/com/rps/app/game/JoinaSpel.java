package com.rps.app.game;

import com.rps.app.player.SpelareObjekt;
import org.springframework.stereotype.Component;


@Component
public class JoinaSpel {
    public SpelRespons mapToGameResponseDTO(SpelObjekt game) {
        SpelRespons gameResponseDTO = new SpelRespons();
        gameResponseDTO.setId(game.getGameId());
        SpelareObjekt playerOne = game.getPlayerOne();
        SpelareObjekt playerTwo = game.getPlayerTwo();
        if (playerOne != null) {
            gameResponseDTO.setPlayerOne(playerOne.getPlayerId().toString());
            gameResponseDTO.setPlayerTwo( playerTwo.getPlayerId().toString());
        }
        gameResponseDTO.setStatus(game.getStatus() != null ? game.getStatus().toString() : "Unknown Position");
        return gameResponseDTO;
    }
}