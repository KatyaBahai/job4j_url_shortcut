package ru.job4j.urlshortcut.security.sitedetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.site.SiteRepository;

@Service
@AllArgsConstructor
public class SiteDetailsServiceImpl  implements UserDetailsService {
    private SiteRepository siteRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String siteDomainName) throws UsernameNotFoundException {
        Site site = siteRepository.findByDomainName(siteDomainName)
                .orElseThrow(() -> new UsernameNotFoundException("Site Not Found with site Domain Name: " + siteDomainName));
        return SiteDetailsImpl.build(site);
    }
}
