package com.decagon.fintrackapp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class RequestCategory extends BaseModel{
    @NotBlank
    @Size(max = 64)
    private String name;

    @OneToOne
    private TransactionType transactionType;

    public RequestCategory(@NotBlank @Size(max = 64) String name) {
        this.name = name;
    }
}
