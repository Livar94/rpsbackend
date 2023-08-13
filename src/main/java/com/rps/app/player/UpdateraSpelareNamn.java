package com.rps.app.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateraSpelareNamn {
    @JsonProperty
    private String name;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


}
