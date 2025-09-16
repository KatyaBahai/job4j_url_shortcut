package ru.job4j.urlshortcut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.convert.UrlConvertRequestDto;
import ru.job4j.urlshortcut.dto.convert.UrlConvertResponseDto;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import ru.job4j.urlshortcut.service.url.UrlService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name = "ConvertController", description = "ConvertController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/urlshortcut/convert")
public class ConvertController {

    private UrlService urlService;

    @Operation(
            summary = "shorten a given url",
            description = "Save a given url, create a short code and send the generated code to the client",
            tags = { "url", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = UrlConvertResponseDto.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/shorten")
    public ResponseEntity<UrlConvertResponseDto> shortenUrl(
            @Valid @RequestBody UrlConvertRequestDto requestDto,
            Authentication authentication) {
        String siteDomainName = authentication.getName();
        return ResponseEntity.ok(urlService.shortenUrl(requestDto, siteDomainName));
    }

    @Operation(
            summary = "expand a url",
            description = "converts the short code back into the original url and redirects the user to the original link",
            tags = { "url", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirects to the original URL"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<Void> expandUrl(
            @PathVariable String shortCode) {
        String fullUrl = urlService.expandUrl(shortCode);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(fullUrl))
                .build();
    }

    @Operation(
            summary = "delete a url by code",
            description = "Delete a url by code by an authorized client",
            tags = { "url", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode, Authentication authentication) {
        String siteDomainName = authentication.getName();
        urlService.deleteUrl(shortCode, siteDomainName);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all urls by site",
            description = "Get all urls that belong to the authenticated website",
            tags = { "url", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = StatsResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/all")
    public ResponseEntity<List<UrlConvertResponseDto>> getAllUrlsBySite(Authentication authentication) {
        String siteDomainName = authentication.getName();
        return ResponseEntity.ok(urlService.getAllUrls(siteDomainName));
    }

}
