package com.rps.app.game;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@NoArgsConstructor
@Data
public class Requests {


    private SpelMove playerMove;
    private UUID playerId;
    private UUID gameId;
}
