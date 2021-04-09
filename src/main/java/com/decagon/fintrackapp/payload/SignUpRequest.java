package com.decagon.fintrackapp.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class SignUpRequest {
    @NotBlank
    @Size(min = 2, max = 40)
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 40)
    private String lastname;

    @NotBlank
    @Size(min = 2, max = 40)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 2, max = 20)
    private String password;
}
