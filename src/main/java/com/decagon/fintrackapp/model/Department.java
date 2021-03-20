package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Table(name = ("departments"))
@Entity
public class Department extends BaseModel{
    private String name;

    @OneToMany
    private List<User> users;
}
