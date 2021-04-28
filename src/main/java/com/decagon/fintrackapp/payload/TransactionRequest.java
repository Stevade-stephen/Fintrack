package com.decagon.fintrackapp.payload;

import com.decagon.fintrackapp.model.ECashType;
import com.decagon.fintrackapp.model.ECategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TransactionRequest {

    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private double amount;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private ECategory category;

//    @Enumerated(EnumType.STRING)
//    private ECashType eCashType;


}
