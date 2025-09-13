package ru.job4j.urlshortcut.repository.url;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.urlshortcut.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByUrl(String url);

    Boolean existsByUrl(String url);

    Url findByCode(String code);

    int deleteByCode(String code);

    Boolean existsByCode(String code);

    List<Url> findAllBySiteDomainName(String siteDomainName);
}
