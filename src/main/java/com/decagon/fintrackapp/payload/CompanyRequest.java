package com.decagon.fintrackapp.payload;

import lombok.Data;

@Data
public class CompanyRequest {

    private String companyName;
    private String companyLogoUrl;
    private String companyEmail;

}
