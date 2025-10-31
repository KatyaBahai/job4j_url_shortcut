package ru.job4j.urlshortcut.service.url;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.url.UrlRepository;
import ru.job4j.urlshortcut.service.site.SiteService;

import java.util.List;

@Service
@AllArgsConstructor
public class UrlStatsServiceImpl implements UrlStatsService {
    private SiteService siteService;
    private UrlRepository urlRepository;

    @Transactional(readOnly = true)
    @Override
    public List<StatsResponseDto> getAllUrlsStats(String login) {
        verifySiteAuthentication(login);
        List<Url> urls = urlRepository.findAllBySiteLogin(login);

        return urls.stream()
                .map(url -> new StatsResponseDto(url.getUrl(), url.getRedirectCount()))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public StatsResponseDto getOneUrlStats(String login, String code) {
        verifySiteAuthentication(login);
        Url url = urlRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("There's no url existing for given short code"));
        return new StatsResponseDto(url.getUrl(), url.getRedirectCount());
    }

    private void verifySiteAuthentication(String login) {
        siteService.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Site not authorized"));
    }
}
