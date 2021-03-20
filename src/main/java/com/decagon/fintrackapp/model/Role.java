package com.decagon.fintrackapp.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
@Getter
@Setter
@Entity
public class Role extends BaseModel{
    private ERole roleType;
}
