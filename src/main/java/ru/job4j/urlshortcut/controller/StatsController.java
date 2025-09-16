package ru.job4j.urlshortcut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import ru.job4j.urlshortcut.service.url.UrlStatsService;

import java.util.List;

@Tag(name = "StatsController", description = "StatsController management APIs")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/urlshortcut/stats")
public class StatsController {
    private UrlStatsService urlStatsService;

    @Operation(
            summary = "Get statistics for all urls",
            description = "Get statistics for each url from a particular registered website, provides a number of each url calls",
            tags = { "statistics", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = StatsResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/all")
    public ResponseEntity<List<StatsResponseDto>> getAllUrlsStatisticsBySite(
            Authentication authentication) {
        String login = authentication.getName();
        return ResponseEntity.ok(urlStatsService.getAllUrlsStats(login));
    }

    @Operation(
            summary = "Get statistics for 1 url",
            description = "Get statistics for 1 url by code, provides a number of url calls",
            tags = { "statistics", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = StatsResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{code}")
    public ResponseEntity<StatsResponseDto> getOneUrlStats(
            Authentication authentication,
            @PathVariable String code) {
        String login = authentication.getName();
        return ResponseEntity.ok(urlStatsService.getOneUrlStats(login, code));
    }
}
