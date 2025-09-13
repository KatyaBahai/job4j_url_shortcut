package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.convert.UrlConvertRequestDto;
import ru.job4j.urlshortcut.dto.convert.UrlConvertResponseDto;
import ru.job4j.urlshortcut.service.url.UrlService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/urlshortcut/convert")
public class ConvertController {

    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlConvertResponseDto> shortenUrl(
            @Valid @RequestBody UrlConvertRequestDto requestDto,
            Authentication authentication) {
        String siteDomainName = authentication.getName();
        return ResponseEntity.ok(urlService.shortenUrl(requestDto, siteDomainName));
    }

    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<UrlConvertResponseDto> expandUrl(
            @PathVariable String shortCode) {
        String fullUrl = urlService.expandUrl(shortCode);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(fullUrl))
                .build();
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode, Authentication authentication) {
        String siteDomainName = authentication.getName();
        urlService.deleteUrl(shortCode, siteDomainName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UrlConvertResponseDto>> getAllUrlsBySite(Authentication authentication) {
        String siteDomainName = authentication.getName();
        return ResponseEntity.ok(urlService.getAllUrls(siteDomainName));
    }

}
