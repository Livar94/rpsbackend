package com.rps.app.game;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Data
public class SpelRespons {
    private UUID id;
    private String playerOne;
    private String playerTwo;
    private String playerOneMove;
    private String playerTwoMove;
    private String status;
    private Integer playerOneWins;
    private Integer playerTwoWins;
    private LocalDateTime lastUpdated;
}
