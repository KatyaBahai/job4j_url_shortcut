package ru.job4j.urlshortcut.service.site;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.authentication.request.RegisterRequestDto;
import ru.job4j.urlshortcut.dto.authentication.response.RegisterResponseDto;
import ru.job4j.urlshortcut.model.Site;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SiteAuthServiceImpl implements SiteAuthService {
    private SiteService siteService;

    @Transactional
    @Override
    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        String siteDomainName = registerRequestDto.getSiteDomainName();
        if (Boolean.TRUE.equals(siteService.existsByDomainName(siteDomainName))) {
            Site site = siteService.findByDomainName(siteDomainName).get();
            return RegisterResponseDto.builder()
                    .login(site.getLogin())
                    .password("***")
                    .registration(false)
                    .build();
        }
        String login = generateLogin();
        String password = generatePassword();

        Site site = Site.builder()
                .domainName(siteDomainName)
                .login(login)
                .password(password)
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
