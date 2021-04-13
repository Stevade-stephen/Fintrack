package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class RequestCategory extends Auditable<String> {
    @NotBlank
    @Size(max = 64)
    private String name;

    //@OneToOne(cascade = {CascadeType.ALL})


    public RequestCategory(@NotBlank @Size(max = 64) String name) {
        this.name = name;
    }
}
