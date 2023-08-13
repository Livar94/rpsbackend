package com.rps.app.game;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rps.app.player.SpelareObjekt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "game_entity")
public class SpelObjekt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private UUID gameId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_one")
    @ToString.Exclude
    private SpelareObjekt playerOne;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_two")
    @ToString.Exclude
    private SpelareObjekt playerTwo;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_one_move")
    private SpelMove playerOneMove;

    @Enumerated(EnumType.STRING)
    @Column(name = "player_two_move")
    private SpelMove playerTwoMove;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Position status;


    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "player_one_wins", columnDefinition = "int default 0")    //Har lagt till två nya kolumner i SpelObjekt
    private int PlayerOneWins;
    @Column (name = "player_two_wins",columnDefinition = "int default 0")
    private int PlayerTwoWins;
}
