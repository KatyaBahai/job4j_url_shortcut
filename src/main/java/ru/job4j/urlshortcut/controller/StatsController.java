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
import ru.job4j.urlshortcut.controller.api.StatsControllerApi;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import ru.job4j.urlshortcut.service.url.UrlStatsService;

import java.util.List;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/urlshortcut/stats")
public class StatsController implements StatsControllerApi {
    private UrlStatsService urlStatsService;

    @GetMapping("/all")
    public ResponseEntity<List<StatsResponseDto>> getAllUrlsStatisticsBySite(
            Authentication authentication) {
        String login = authentication.getName();
        return ResponseEntity.ok(urlStatsService.getAllUrlsStats(login));
    }

    @GetMapping("/{code}")
    public ResponseEntity<StatsResponseDto> getOneUrlStats(
            Authentication authentication,
            @PathVariable String code) {
        String login = authentication.getName();
        return ResponseEntity.ok(urlStatsService.getOneUrlStats(login, code));
    }
}
