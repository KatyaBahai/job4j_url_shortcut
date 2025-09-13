package ru.job4j.urlshortcut.service.url;

import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;

import java.util.List;

public interface UrlStatsService {
    List<StatsResponseDto> getAllUrlsStats(String siteDomainName);

    StatsResponseDto getOneUrlStats(String siteDomainName, String url);
}
