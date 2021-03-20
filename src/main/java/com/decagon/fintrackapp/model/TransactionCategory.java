package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table(name = ("transaction_categories"))
@Entity
public class TransactionCategory extends BaseModel{

    private ECategory name;

    @OneToMany
    private List<Transaction> transactions;
}
