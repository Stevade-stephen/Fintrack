package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Table(name = ("companies"))
@Entity
public class Company extends Auditable<String> {

    @NotBlank
    @Column(name = ("company_name"))
    private String companyName;

    @NotBlank
    @Column(name = ("company_logo_url"))
    @Lob
    private String companyLogoUrl;

    @NotBlank
    @Column(name = ("company_email"))
    private String companyEmail;

    @OneToOne
    private User companyCeo;

    @OneToOne
    private User financialController;

    @OneToMany
    Set<User> userList;
}
