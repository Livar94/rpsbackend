package com.rps.app.game;

import com.rps.app.player.SpelareId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/games")
public class SpelKontroll {

    private final SpelServices gameService;


    @Autowired
    public SpelKontroll(SpelServices gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/game")
    public ResponseEntity<SpelObjekt> startGame(@RequestBody SpelareId playerIdDTO) {
        SpelObjekt newGame = gameService.startGame(playerIdDTO);
        return new ResponseEntity<>(newGame, HttpStatus.OK);
    }





    @GetMapping("/games")
    public ResponseEntity<List<SpelRespons>> getOpenGames() {
        List<SpelRespons> openGames = gameService.getOpenGames();
        return ResponseEntity.ok(openGames);
    }







    @PostMapping("/join")
    public ResponseEntity<SpelRespons> joinGame(@RequestHeader("gameId") UUID gameId, @RequestBody SpelareId playerIdDTO) {
        return gameService.joinGame(gameId, playerIdDTO);
    }






    @PostMapping("/{gameId}")
    public SpelRespons getGameInfo(@PathVariable UUID gameId, @RequestBody SpelareId playerIdDTO) {
        return gameService.getGameInfo(gameId, playerIdDTO);
    }




    @PostMapping("/move")
    public SpelRespons move(@Validated @RequestBody Requests moveRequest) {


        return gameService.move(
                moveRequest.getPlayerMove(),
                new SpelareId(moveRequest.getPlayerId()),
                moveRequest.getGameId()
        );
    }



}





