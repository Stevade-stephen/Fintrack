package com.decagon.fintrackapp.model;

import javax.persistence.Table;

@Table(name = ("roles"))
public enum ERole {
    SUPER_ADMIN,
    REQUESTER,
    LINE_MANAGER,
    FINANCIAL_CONTROLLER,
    CEO
}
