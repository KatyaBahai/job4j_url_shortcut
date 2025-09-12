package ru.job4j.urlshortcut.repository.url;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.model.Url;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Site> findByUrl(String url);

    Boolean existsByUrl(String url);

    Site findByCode(String code);

    Boolean existsByCode(String code);
}
