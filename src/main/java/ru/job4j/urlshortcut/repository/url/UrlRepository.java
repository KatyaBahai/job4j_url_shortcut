package ru.job4j.urlshortcut.repository.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.urlshortcut.model.Url;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Url findByUrl(String url);

    Boolean existsByUrl(String url);

    Optional<Url> findByCode(String code);

    int deleteByCode(String code);

    Boolean existsByCode(String code);

    List<Url> findAllBySiteDomainName(String siteDomainName);

    @Modifying
    @Query("UPDATE Url u SET u.redirectCount = u.redirectCount + 1 WHERE u.code = :code")
    void incrementRedirectCount(@Param("code") String code);

    List<Url> findAllBySiteLogin(String login);
}
