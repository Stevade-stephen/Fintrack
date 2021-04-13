package com.decagon.fintrackapp.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Role extends Auditable{


    @Enumerated(EnumType.STRING)
    private ERole appUserRole;

    public Role(ERole appUserRole) {
        this.appUserRole = appUserRole;
    }
    public Role() {
    }

    @OneToOne
    private Approval approval;

    @ManyToMany
    private List<Transaction>transactions;


}
