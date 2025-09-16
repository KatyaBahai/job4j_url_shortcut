package ru.job4j.urlshortcut.service.site;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.authentication.request.RegisterRequestDto;
import ru.job4j.urlshortcut.dto.authentication.response.RegisterResponseDto;
import ru.job4j.urlshortcut.model.Site;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SiteAuthServiceImpl implements SiteAuthService {
    private SiteService siteService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        String siteDomainName = registerRequestDto.getSiteDomainName();
        if (Boolean.TRUE.equals(siteService.existsByDomainName(siteDomainName))) {
            Site site = siteService.findByDomainName(siteDomainName)
                    .orElseThrow(() -> new IllegalStateException("Site should exist but was not found"));
            return RegisterResponseDto.builder()
                    .login(site.getLogin())
                    .password("***")
                    .registration(false)
                    .build();
        }
        String login = generateLogin();
        String password = generatePassword();
        String encodedPassword = passwordEncoder.encode(password);

        Site site = Site.builder()
                .domainName(siteDomainName)
                .login(login)
                .password(encodedPassword)
                .build();

        siteService.save(site);

        return RegisterResponseDto.builder()
                .login(login)
                .password(password)
                .registration(true)
                .build();
    }

    private String generateLogin() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
