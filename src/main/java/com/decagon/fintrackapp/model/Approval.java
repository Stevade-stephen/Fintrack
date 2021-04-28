package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = ("approvals"))
@Entity
public class Approval extends Auditable<String>{
    private String status;
    @Enumerated(EnumType.STRING)
    private EApproval isApprovedByLineManager;
    @Enumerated(EnumType.STRING)
    private EApproval isApprovedByCEO;
    @Enumerated(EnumType.STRING)
    private EApproval isApprovedByFinancialController;


    @OneToOne
    private Transaction transaction;
}
