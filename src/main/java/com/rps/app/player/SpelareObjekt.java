package com.rps.app.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rps.app.game.SpelObjekt;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Lomboks annotationer för att automatiskt lägga till nedan komponenter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "player_entity")
public class SpelareObjekt {


    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "player_name")
    private String playerName;
    
    @OneToMany(mappedBy = "playerOne", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<SpelObjekt> playerOneGames = new ArrayList<>();

    @OneToMany(mappedBy = "playerTwo", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<SpelObjekt> playerTwoGames = new ArrayList<>();




    //Getters Setters
    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public List<SpelObjekt> getPlayerOneGames() {
        return playerOneGames;
    }

    public void setPlayerOneGames(List<SpelObjekt> playerOneGames) {
        this.playerOneGames = playerOneGames;
    }

    public List<SpelObjekt> getPlayerTwoGames() {
        return playerTwoGames;
    }

    public void setPlayerTwoGames(List<SpelObjekt> playerTwoGames) {
        this.playerTwoGames = playerTwoGames;
    }


    public void setPlayerOneGame(UUID fromString) {
    }

    public void setPlayerTwoGame(UUID fromString) {
    }
}
