package ru.job4j.urlshortcut.service.site;

import ru.job4j.urlshortcut.model.Site;

import java.util.List;
import java.util.Optional;

public interface SiteService {
    Site save(Site site);

    Boolean existsByDomainName(String siteDomainName);

    Optional<Site> findByLogin(String login);

    Optional<Site> findByDomainName(String siteDomainName);
}
