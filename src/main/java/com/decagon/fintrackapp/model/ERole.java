package com.decagon.fintrackapp.model;

import javax.persistence.Table;

@Table(name = ("roles"))
public enum ERole {

    ROLE_SUPER_ADMIN(""),
    ROLE_REQUESTER(""),
    ROLE_LINE_MANAGER(""),
    ROLE_FINANCIAL_CONTROLLER(""),
    ROLE_CEO("");

     ERole(String description){
     }

}
