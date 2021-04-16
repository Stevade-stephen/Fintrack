package com.decagon.fintrackapp.serviceImp.super_admin_service;

import com.decagon.fintrackapp.model.Company;
import com.decagon.fintrackapp.payload.ApiResponse;
import com.decagon.fintrackapp.payload.CompanyRequest;
import com.decagon.fintrackapp.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.ws.Response;
import java.net.URI;

@Service
public class CompanyServiceImpl {

    private final CompanyRepository companyRepository;
    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResponseEntity<?> updateCompanyProfile (CompanyRequest companyRequest){
        Company company = companyRepository.getOne(1L);
        company.setCompanyEmail(companyRequest.getCompanyEmail());
        company.setCompanyName(companyRequest.getCompanyName());
        company.setCompanyLogoUrl(companyRequest.getCompanyLogoUrl());

        companyRepository.save(company);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/company/{companyName}")
                .buildAndExpand(company.getCompanyName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(
                true, "Company profile updated successfully"));
    }
}
