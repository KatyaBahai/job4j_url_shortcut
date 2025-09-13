package ru.job4j.urlshortcut.service.url;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.convert.UrlConvertRequestDto;
import ru.job4j.urlshortcut.dto.convert.UrlConvertResponseDto;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.url.UrlRepository;
import ru.job4j.urlshortcut.service.site.SiteService;

import org.springframework.transaction.annotation.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class UrlServiceImpl implements UrlService {
    private final static int SHORT_CODE_LENGTH = 8;
    private UrlRepository urlRepository;
    private SiteService siteService;

    @Transactional
    @Override
    public UrlConvertResponseDto shortenUrl(UrlConvertRequestDto urlConvertRequestDto, String siteDomainName) {
        Site site = siteService.findByDomainName(siteDomainName)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated site not found in DB: " + siteDomainName));

        String originalLink = urlConvertRequestDto.getUrlToTransform();
        if (urlRepository.existsByUrl(originalLink)) {
            throw new IllegalArgumentException(
                    "This link is already in the system: %s");
        }
        String shortCode = generateShortCode();
        Url url = Url.builder()
                .code(shortCode)
                .site(site)
                .url(originalLink)
                .build();
        urlRepository.save(url);
        return new UrlConvertResponseDto(shortCode);
    }

    @Transactional
    @Override
    public String expandUrl(String shortCode) {
        if (!urlRepository.existsByCode(shortCode)) {
            throw new IllegalArgumentException("There's no url for this short code");
        }
        Url originalUrl = urlRepository.findByCode(shortCode);
        urlRepository.incrementRedirectCount(shortCode);
        return originalUrl.getUrl();
    }

    @Transactional
    @Override
    public void deleteUrl(String shortCode, String siteDomainName) {
        verifySiteAuthentication(siteDomainName);
        urlRepository.deleteByCode(shortCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UrlConvertResponseDto> getAllUrls(String siteDomainName) {
        verifySiteAuthentication(siteDomainName);
        List<Url> urls = urlRepository.findAllBySiteDomainName(siteDomainName);

        return urls.stream()
                .map(url -> new UrlConvertResponseDto(url.getUrl()))
                .toList();
    }

    private String generateShortCode() {
        String shortCode;
        do {
            shortCode = UUID.randomUUID().toString().substring(0, SHORT_CODE_LENGTH);
        } while (urlRepository.existsByCode(shortCode));

        return shortCode;
    }

    private String getDomainFromURL(String stringUrl) {
        String siteDomainName = "";
        try {
            URL url = new URL(stringUrl);
            siteDomainName = url.getHost();
        } catch (MalformedURLException e) {
            log.error("Incorrect URL: {}", e.getMessage());
        }
        return siteDomainName;
    }

    private void verifySiteAuthentication(String siteDomainName) {
        siteService.findByDomainName(siteDomainName)
                .orElseThrow(() -> new IllegalArgumentException("Site not found"));
    }
}
