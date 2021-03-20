package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = ("transaction_types"))
@Entity
public class TransactionType extends BaseModel{

    private String description;
    private String name;
    private String min;
    private String max;
    private String status;
}
