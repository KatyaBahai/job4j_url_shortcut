package ru.job4j.urlshortcut.service.url;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.url.UrlRepository;
import ru.job4j.urlshortcut.service.site.SiteService;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UrlStatsServiceImpl implements UrlStatsService {
    private SiteService siteService;
    private UrlRepository urlRepository;

    @Transactional(readOnly = true)
    @Override
    public List<StatsResponseDto> getAllUrlsStats(String siteDomainName) {
        verifySiteAuthentication(siteDomainName);
        List<Url> urls = urlRepository.findAllBySiteDomainName(siteDomainName);

        return urls.stream()
                .map(url -> new StatsResponseDto(url.getUrl(), url.getRedirectCount()))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public StatsResponseDto getOneUrlStats(String siteDomainName, String code) {
        verifySiteAuthentication(siteDomainName);
        if (!urlRepository.existsByCode(code)) {
            throw new IllegalArgumentException("There's no url existing for the given short code");
        }
        Url url = urlRepository.findByCode(code);
        return new StatsResponseDto(url.getUrl(), url.getRedirectCount());
    }

    private void verifySiteAuthentication(String siteDomainName) {
        siteService.findByDomainName(siteDomainName)
                .orElseThrow(() -> new IllegalArgumentException("Site not authorized"));
    }
}
