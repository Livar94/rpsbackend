package com.rps.app.game;

import org.springframework.stereotype.Component;


@Component
public class OpenGame {
    public static SpelRespons mapToDTO(SpelObjekt game) {
        SpelRespons gameResponseDTO = new SpelRespons();
        gameResponseDTO.setId(game.getGameId());
        gameResponseDTO.setPlayerOne(game.getPlayerOne() != null ? game.getPlayerOne().getPlayerName() : "Unknown");
        gameResponseDTO.setPlayerTwo(game.getPlayerTwo() != null ? game.getPlayerTwo().getPlayerName() : "Unknown");
        gameResponseDTO.setStatus(game.getStatus() != null ? game.getStatus().toString() : "Unknown Position");


        if (game.getPlayerOneMove() != null && game.getPlayerTwoMove() != null) {
            gameResponseDTO.setPlayerOneMove(game.getPlayerOneMove().toString());
            gameResponseDTO.setPlayerTwoMove(game.getPlayerTwoMove().toString());
        } else {
            gameResponseDTO.setPlayerOneMove(null);
            gameResponseDTO.setPlayerTwoMove(null);
        }

        gameResponseDTO.setPlayerOneWins(game.getPlayerOneWins());
        gameResponseDTO.setPlayerTwoWins(game.getPlayerTwoWins());
        gameResponseDTO.setLastUpdated(game.getLastUpdated());
        return gameResponseDTO;
    }
}
