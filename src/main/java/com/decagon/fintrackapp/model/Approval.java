package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = ("approvals"))
@Entity
public class Approval extends BaseModel{
    private String status;

    /**
     * FKs from
     * ETransactionStatus is an enum
     */
    private Transaction transaction;
    private User user;
    private ERole ERole;
}
