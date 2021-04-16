package com.decagon.fintrackapp.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class RequestCategory extends Auditable<String> {
    @NotBlank
    @Size(max = 64)
    private String name;
    private Long min;
    private Long max;

}
