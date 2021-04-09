package com.decagon.fintrackapp.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateRequest {
    @NotBlank(message = "Firstname cannot be empty!")
    @Size(min = 4, max = 40)
    @Order(1)
    private String firstname;

    @NotBlank(message = "Lastname cannot be empty!")
    @Size(min = 4, max = 40)
    @Order(2)
    private String lastname;

    @NotBlank(message = "Phone cannot be empty!")
    @Size(max = 40)
    private String phone;
}
