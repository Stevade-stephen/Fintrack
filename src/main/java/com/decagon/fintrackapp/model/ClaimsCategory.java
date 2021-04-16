package com.decagon.fintrackapp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ClaimsCategory extends Auditable<String> {
    @NotBlank
    @Size(max = 64)
    private String name;
    private Long min;
    private Long max;

}
