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
public class UrlServiceImpl implements UrlService {
    private final static int SHORT_CODE_LENGTH = 8;
    private UrlRepository urlRepository;
    private SiteService siteService;

    @Transactional
    @Override
    public UrlConvertResponseDto shortenUrl(UrlConvertRequestDto urlConvertRequestDto, String login) {
        Site site = siteService.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated site not found in DB: " + login));

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
                .redirectCount(0L)
                .build();
        urlRepository.save(url);
        return new UrlConvertResponseDto(shortCode);
    }

    @Override
    public String expandUrl(String shortCode) {
        Url originalUrl = urlRepository.findByCode(shortCode)
                .orElseThrow(() -> new IllegalArgumentException("There's no url for this short code"));
        incrementRedirectCount(shortCode);
        return originalUrl.getUrl();
    }

    @Transactional
    public void incrementRedirectCount(String shortCode) {
        urlRepository.incrementRedirectCount(shortCode);
    }

    @Transactional
    @Override
    public void deleteUrl(String shortCode, String siteDomainName) {
        verifySiteAuthentication(siteDomainName);
        urlRepository.deleteByCode(shortCode);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UrlConvertResponseDto> getAllUrls(String login) {
        verifySiteAuthentication(login);
        List<Url> urls = urlRepository.findAllBySiteLogin(login);

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

    private void verifySiteAuthentication(String login) {
        siteService.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("Site not found"));
    }
}
