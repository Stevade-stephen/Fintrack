package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table(name = ("transaction_types"))
@Entity
public class TransactionType extends BaseModel{

    private String description;
    private String name;

    //do we need min and max
    private String min;
    private String max;
    private String status;

    @ManyToMany
    private List<Role> roles;
}
