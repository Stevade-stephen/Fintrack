package com.decagon.fintrackapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class ClaimsCategory extends Auditable<String> {
    @NotBlank
    @Size(max = 64)
    private String name;

    public ClaimsCategory(@NotBlank @Size(max = 64) String name) {
        this.name = name;
    }

    public ClaimsCategory() {
    }
}
