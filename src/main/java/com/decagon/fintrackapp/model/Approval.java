package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = ("approvals"))
@Entity
public class Approval extends Auditable<String>{
    private String status;
    private boolean isApprovedByLineManager;
    private boolean isApprovedByCEO;
    private boolean isApprovedByFinancialController;


    @OneToOne
    private Transaction transaction;
}
