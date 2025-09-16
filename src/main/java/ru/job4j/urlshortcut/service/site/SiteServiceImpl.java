package ru.job4j.urlshortcut.service.site;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.site.SiteRepository;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {
    private SiteRepository siteRepository;

    @Transactional
    @Override
    public Site save(Site site) {
        try {
        URL url = new URL(site.getDomainName());
        String domainString = url.getHost().toLowerCase();
            site.setDomainName(domainString);
            return siteRepository.save(site);
    } catch (MalformedURLException e) {
                log.error("Incorrect URL: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid URL format: " + site.getDomainName(), e);
            }
        }

    @Override
    public Boolean existsByDomainName(String siteDomainName) {
        return siteRepository.existsByDomainName(siteDomainName);
    }

    @Override
    public Optional<Site> findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    @Override
    public Optional<Site> findByDomainName(String siteDomainName) {
        return siteRepository.findByDomainName(siteDomainName);
    }

}
