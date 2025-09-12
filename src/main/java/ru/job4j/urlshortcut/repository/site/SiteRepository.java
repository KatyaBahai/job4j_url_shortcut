package ru.job4j.urlshortcut.repository.site;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByDomainName(String siteDomainName);

    Boolean existsByDomainName(String siteDomainName);
}
