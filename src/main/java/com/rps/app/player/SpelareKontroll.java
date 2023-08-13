package com.rps.app.player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;




@CrossOrigin(origins = "*")
@RestController

@RequestMapping(value = "/api/user",
        produces = MediaType.APPLICATION_JSON_VALUE
        )

public class SpelareKontroll {

private final SpelareSevrices playerService;

    @Autowired
    public SpelareKontroll(SpelareSevrices playerService) {
        this.playerService = playerService;
    }







    //Senaste 1.2
    @PostMapping("/auth/token")
    public ResponseEntity<UUID> generateToken() {
        SpelareObjekt playerEntity = playerService.generateToken();
        return new ResponseEntity<>(playerEntity.getPlayerId(), HttpStatus.OK);
    }


    @PostMapping("/name")
    public ResponseEntity<Void> setName(@RequestHeader("Token") UUID playerId, @RequestBody UpdateraSpelareNamn requestDTO) {
        playerService.setName(playerId, requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


