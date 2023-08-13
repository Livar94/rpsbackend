package com.rps.app.game;

import com.rps.app.player.SpelareId;
import com.rps.app.player.SpelareObjekt;
import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


import com.rps.app.player.SpelareRep;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class SpelServices {
    private final SpelRep gameRepository;
    private final SpelareRep playerRepository;
    private final JoinaSpel joinGameMapper;
    private static final Logger log = LoggerFactory.getLogger(SpelServices.class);




    public SpelObjekt startGame(SpelareId playerIdDTO) {
        UUID playerId = playerIdDTO.getPlayerId();
        System.out.println("Player Id received: " + playerId);
        Optional<SpelareObjekt> playerOptional = playerRepository.findById(playerId);
        if (playerOptional.isEmpty()) {
            throw new RuntimeException("Player not found for id: " + playerId);
        }
        SpelareObjekt player = playerOptional.get();

        SpelObjekt game = new SpelObjekt();
        game.setStatus(Position.OPEN);
        game.setPlayerOne(player);

        try {
            game = gameRepository.save(game);
            System.out.println("Game created: " + game);
            player.getPlayerOneGames().add(game);
            playerRepository.save(player);
        } catch (Exception e) {
            System.out.println("Exception when creating game: " + e.getMessage());
            throw new RuntimeException("Game couldn't be created", e);
        }

        return game;
    }



    public List<SpelRespons> getOpenGames() {
        List<SpelObjekt> openGames = gameRepository.findByStatus(Position.OPEN);

        return openGames.stream()
                .map(OpenGame::mapToDTO)
                .collect(Collectors.toList());
    }



    public ResponseEntity<SpelRespons> joinGame(UUID gameId, SpelareId playerIdDTO) {
        UUID playerId = playerIdDTO.getPlayerId();
        Optional<SpelObjekt> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            if (game.get().getStatus().equals(Position.OPEN)) {
                Optional<SpelareObjekt> player = playerRepository.findById(playerId);
                if (player.isPresent()) {
                    game.get().setPlayerTwo(player.get());
                    player.get().setPlayerTwoGame(game.get().getGameId());
                    game.get().setStatus(Position.ACTIVE);
                    gameRepository.save(game.get());
                    playerRepository.save(player.get());
                    return new ResponseEntity<>(joinGameMapper.mapToGameResponseDTO(game.get()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }





    public SpelRespons getGameInfo(UUID gameId, SpelareId playerId) {
        SpelObjekt game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));


        if (game.getPlayerOne() != null) {
            SpelareObjekt playerOne = playerRepository.findById(game.getPlayerOne().getPlayerId()).orElse(null);
            game.setPlayerOne(playerOne);
        }
        if (game.getPlayerTwo() != null) {
            SpelareObjekt playerTwo = playerRepository.findById(game.getPlayerTwo().getPlayerId()).orElse(null);
            game.setPlayerTwo(playerTwo);
        }
        System.out.println("Player one: " + game.getPlayerOne());
        System.out.println("Player two: " + game.getPlayerTwo());

        SpelRespons gameResponseDTO = OpenGame.mapToDTO(game);

        if (game.getPlayerOne() != null && game.getPlayerOne().getPlayerId().equals(playerId.getPlayerId())) {
            return gameResponseDTO;
        } else if (game.getPlayerTwo() != null && game.getPlayerTwo().getPlayerId().equals(playerId.getPlayerId())) {
            return gameResponseDTO;
        } else {
            throw new RuntimeException("Player not found in the game");
        }

    }





    public SpelRespons move(SpelMove playerMove, SpelareId playerIdDto, UUID gameId) {
        UUID playerId = playerIdDto.getPlayerId();
        log.info("Player SpelMove: {}, Player ID: {}, Game ID: {}", playerMove, playerId, gameId);

        if (playerId == null) {
            log.error("Player ID cannot be null");
            throw new IllegalArgumentException("Player ID cannot be null");
        }
        if (gameId == null) {
            log.error("Game ID cannot be null");
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        log.info("Fetching game with ID: {}", gameId);
        SpelObjekt game = gameRepository.findById(gameId).orElseThrow(() -> {
            log.error("Game not found");
            return new EntityNotFoundException("Game not found");
        });


        SpelareObjekt player = null;
        if (game.getPlayerOne() != null && game.getPlayerOne().getPlayerId().equals(playerId)) {
            player = game.getPlayerOne();
        } else if (game.getPlayerTwo() != null && game.getPlayerTwo().getPlayerId().equals(playerId)) {
            player = game.getPlayerTwo();
        }

        if (player == null) {
            log.error("Player not found");
            throw new EntityNotFoundException("Player not found");
        }

        log.info("Fetched player: {}", player);
        log.info("Fetched game: {}", game);

        if (game.getPlayerOne().getPlayerId().equals(playerId)) {
            game.setPlayerOneMove(playerMove);
        } else if (game.getPlayerTwo().getPlayerId().equals(playerId)) {
            game.setPlayerTwoMove(playerMove);
        } else {
            throw new IllegalArgumentException("Player is not part of the game");
        }

        if (game.getPlayerOneMove() != null && game.getPlayerTwoMove() != null) {
            SpelMove playerOneMove = game.getPlayerOneMove();
            SpelMove playerTwoMove = game.getPlayerTwoMove();


            if ((playerOneMove == SpelMove.ROCK && playerTwoMove == SpelMove.SCISSORS) ||
                    (playerOneMove == SpelMove.PAPER && playerTwoMove == SpelMove.ROCK) ||
                    (playerOneMove == SpelMove.SCISSORS && playerTwoMove == SpelMove.PAPER)) {
                game.setStatus(Position.PLAYER_ONE_WIN);
            } else if (playerOneMove == playerTwoMove) {
                game.setStatus(Position.DRAW);
            } else {
                game.setStatus(Position.PLAYER_TWO_WIN);
            }


            int player1Wins = game.getPlayerOneWins();
            int player2Wins = game.getPlayerTwoWins();
            if (game.getStatus() == Position.PLAYER_ONE_WIN) {
                player1Wins++;
            } else if (game.getStatus() == Position.PLAYER_TWO_WIN) {
                player2Wins++;
            }
            game.setPlayerOneWins(player1Wins);
            game.setPlayerTwoWins(player2Wins);


            if (player1Wins == 3 || player2Wins == 3) {
                if (player1Wins > player2Wins) {
                    game.setStatus(Position.PLAYER_ONE_IS_THE_WINNER);
                    log.info("Player one wins the game by 3 - {} !", player2Wins);
                } else {
                    game.setStatus(Position.PLAYER_TWO_IS_THE_WINNER);
                    log.info("Player two wins the game by 3 - {} !", player1Wins);
                }
            }
            game.setPlayerOneMove(null);
            game.setPlayerTwoMove(null);
        }

        game.setLastUpdated(LocalDateTime.now());
        gameRepository.save(game);

        SpelRespons gameResponseDTO = OpenGame.mapToDTO(game);

        log.info("Game response: {}", gameResponseDTO);
        return gameResponseDTO;
    }


}