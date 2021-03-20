package com.decagon.fintrackapp.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Invite {

    private String email;
    private String token;
    private String status;

}
