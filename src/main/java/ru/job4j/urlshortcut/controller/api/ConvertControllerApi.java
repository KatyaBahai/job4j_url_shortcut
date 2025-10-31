package ru.job4j.urlshortcut.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.job4j.urlshortcut.dto.convert.UrlConvertRequestDto;
import ru.job4j.urlshortcut.dto.convert.UrlConvertResponseDto;

import java.util.List;

@Tag(name = "ConvertController", description = "ConvertController management APIs")
public interface ConvertControllerApi {
    @Operation(
            summary = "shorten a given url",
            description = "Save a given url, create a short code and send the generated code to the client",
            tags = { "url", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UrlConvertResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<UrlConvertResponseDto> shortenUrl(UrlConvertRequestDto requestDto,
                                                            Authentication authentication);

    @Operation(
            summary = "expand a url",
            description = "converts the short code back into the original url and redirects the user to the original link",
            tags = { "url", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirects to the original URL"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<Void> expandUrl(String shortCode);

    @Operation(
            summary = "delete a url by code",
            description = "Delete a url by code by an authorized client",
            tags = { "url", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<Void> deleteUrl(String shortCode, Authentication authentication);

    @Operation(
            summary = "Get all urls by site",
            description = "Get all urls that belong to the authenticated website",
            tags = { "url", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = UrlConvertResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<List<UrlConvertResponseDto>> getAllUrlsBySite(Authentication authentication);
}
