package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.controller.api.AuthControllerApi;
import ru.job4j.urlshortcut.dto.authentication.request.AuthRequestDto;
import ru.job4j.urlshortcut.dto.authentication.request.RegisterRequestDto;
import ru.job4j.urlshortcut.dto.authentication.response.AuthResponseDto;
import ru.job4j.urlshortcut.dto.authentication.response.RegisterResponseDto;
import ru.job4j.urlshortcut.security.jwt.JwtUtils;
import ru.job4j.urlshortcut.security.sitedetails.SiteDetailsImpl;
import ru.job4j.urlshortcut.service.site.SiteAuthService;

import javax.validation.Valid;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/urlshortcut/auth")
public class AuthController implements AuthControllerApi {
    @Autowired
    private SiteAuthService siteAuthService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerSite(
            @Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto registerDTO = siteAuthService.register(registerRequestDto);
        return ResponseEntity.ok(registerDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> authenticateUser(
            @Valid @RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getLogin(), authRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        SiteDetailsImpl siteDetails = (SiteDetailsImpl) authentication.getPrincipal();
        return ResponseEntity
                .ok(new AuthResponseDto(jwt, siteDetails.getId(), siteDetails.getUsername()));
    }
}
