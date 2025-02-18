package isen.projet_dp_api.controller.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dto.CompanyDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Company", description = "Endpoints for managing user-related operations")
public interface CompanyController {

    @PutMapping("/api/company")
    @Operation(summary = "Register a company", description = "Register a new company with the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Company registered successfully. The response contains no body but includes a header with the JWT token."),
                    @ApiResponse(responseCode = "400", description = "Invalid company data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            })
    ResponseEntity<String> registerCompany(@RequestBody @Valid CompanyDTO companyDTO);


    @GetMapping("/api/company/{name}")
    @Operation(summary="Get a company by her name", description = "Get a company by her id",
        responses = {
                @ApiResponse(responseCode = "201", description = "Company found successfully."),
                @ApiResponse(responseCode = "400", description = "Invalid company data", content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiException.ErrorResponse.class)
                )),
                @ApiResponse(responseCode = "404", description = "Company doesn't exist", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiException.ErrorResponse.class)
                )),
                @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    })
    ResponseEntity<CompanyDAO> getCompanyByName(@PathVariable @Valid String name);

}