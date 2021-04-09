package com.decagon.fintrackapp.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryDto {
    @NotBlank
    @Size(max = 64)
    private String name;
}
