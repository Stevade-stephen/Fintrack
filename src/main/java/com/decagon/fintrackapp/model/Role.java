package com.decagon.fintrackapp.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Role extends BaseModel{
    @Enumerated(EnumType.STRING)
    private ERole appUserRole;

    @OneToOne
    private Approval approval;

    @ManyToMany
    List<Transaction>transactions;
}
