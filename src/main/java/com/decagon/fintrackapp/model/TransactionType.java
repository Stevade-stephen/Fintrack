package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table(name = ("transaction_types"))
@Entity
public class TransactionType extends BaseModel{

    private String description;
    private ECashType eCashType;

    //do we need min and max
    private Long min;
    private Long max;

    @ManyToMany
    private List<User> approvalList;

//    @ManyToMany
//    private List<Role> roles;
}
