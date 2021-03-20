package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = ("transaction_categories"))
@Entity
public class TransactionCategory extends BaseModel{

    private ECategory name;
}
