package com.decagon.fintrackapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Table(name = ("companies"))
@Entity
public class Company extends Auditable{

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
}
