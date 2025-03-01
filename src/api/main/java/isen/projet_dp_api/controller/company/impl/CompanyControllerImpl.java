package isen.projet_dp_api.controller.company.impl;

import isen.projet_dp_api.controller.company.CompanyController;
import isen.projet_dp_api.model.dto.CompanyDTO;
import isen.projet_dp_api.service.CompanyService;
import isen.projet_dp_api.utils.ApiStrings;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class CompanyControllerImpl implements CompanyController {

    private final CompanyService companyService;

    public CompanyControllerImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    public ResponseEntity<CompanyDTO> registerCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        log.debug(ApiStrings.REGISTERING_COMPANY,
                companyDTO.getName());
        return new ResponseEntity<>(companyService.registerCompany(companyDTO), HttpStatus.CREATED);
    }

    public ResponseEntity<CompanyDTO> getCompanyByName(@Valid @PathVariable String name) {
        log.debug(ApiStrings.GET_COMPANY,
                name);
        return new ResponseEntity<>(companyService.getCompanyByName(name), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        log.debug(ApiStrings.GET_COMPANIES);
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteCompanyByName(@PathVariable String name) {
        log.debug(ApiStrings.DELETE_COMPANY, name);
        companyService.deleteCompanyByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
