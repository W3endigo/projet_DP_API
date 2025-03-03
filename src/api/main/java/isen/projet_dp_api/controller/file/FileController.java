package isen.projet_dp_api.controller.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File", description = "Endpoints for managing file-related operations")
public interface FileController {

    @PostMapping(value = "/api/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a file", description = "Upload a file to the server",
            responses = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid file data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<String> putFile(@RequestParam("file") MultipartFile file, @RequestParam("projectName") String projectName);

    // download file endpoint that takes a list of file name and a projectName and then return a file or a zip,
    // depending on the number of file required
    @PostMapping(value = "/api/files/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Download a file", description = "Download a file from the server",
            responses = {
                    @ApiResponse(responseCode = "200", description = "File downloaded successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid file data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<InputStreamResource> getFile(@RequestBody List<String> fileNames, @RequestParam("projectName") String projectName);

}