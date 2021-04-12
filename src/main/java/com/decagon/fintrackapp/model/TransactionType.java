package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name = ("transaction_types"))
@Entity
public class TransactionType{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private ECashType eCashType;

    //do we need min and max
    private Long min;
    private Long max;

//    @ManyToMany
//    private List<User> approvalList;

    public TransactionType(String description, Long min, Long max) {
        this.description = description;
        this.min = min;
        this.max = max;
    }

    public TransactionType() {

    }

    //    @ManyToMany
//    private List<Role> roles;
}
