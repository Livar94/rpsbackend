package com.rps.app.player;

import lombok.*;


import java.util.UUID;


@NoArgsConstructor
@Data
public class SpelareId {




   UUID playerId;

 public SpelareId(UUID playerId) {
    this.playerId = playerId;
 }

}
