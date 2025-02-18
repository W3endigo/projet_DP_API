package isen.projet_dp_api.controller.company.impl;

import isen.projet_dp_api.controller.company.CompanyController;
import isen.projet_dp_api.model.dao.CompanyDAO;
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

@Log4j2
@RestController
public class CompanyControllerImpl implements CompanyController {

    private final CompanyService companyService;

    public CompanyControllerImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    public ResponseEntity<String> registerCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        log.debug(ApiStrings.REGISTERING_COMPANY,
                companyDTO.getName());
        companyService.registerCompany(companyDTO);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    public ResponseEntity<CompanyDAO> getCompanyByName(@Valid @PathVariable String name) {
        log.debug(ApiStrings.GET_COMPANY,
                name);
        var company = companyService.getCompanyByName(name);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }


}
