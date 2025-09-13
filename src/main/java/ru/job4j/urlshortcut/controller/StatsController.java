package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.convert.UrlConvertRequestDto;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import ru.job4j.urlshortcut.service.url.UrlStatsService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/urlshortcut/stats")
public class StatsController {
    private UrlStatsService urlStatsService;

    @GetMapping("/all")
    public ResponseEntity<List<StatsResponseDto>> getAllUrlsStatisticsBySite(
            Authentication authentication) {
        String siteDomainName = authentication.getName();
        return ResponseEntity.ok(urlStatsService.getAllUrlsStats(siteDomainName));
    }

    @GetMapping("/{code}")
    public ResponseEntity<StatsResponseDto> getAllUrlsBySite(
            Authentication authentication,
            @PathVariable String code) {
        String siteDomainName = authentication.getName();
        return ResponseEntity.ok(urlStatsService.getOneUrlStats(siteDomainName, code));
    }
}
