package com.rps.app.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateraSpelare {

    @JsonProperty
    String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
