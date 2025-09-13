package ru.job4j.urlshortcut.service.url;

import ru.job4j.urlshortcut.dto.convert.UrlConvertRequestDto;
import ru.job4j.urlshortcut.dto.convert.UrlConvertResponseDto;

import java.util.List;

public interface UrlService {
    UrlConvertResponseDto shortenUrl(UrlConvertRequestDto requestDto, String siteDomainName);

    String expandUrl(String shortCode);

    void deleteUrl(String shortCode, String siteDomainName);

    List<UrlConvertResponseDto> getAllUrls(String siteDomainName);
}
