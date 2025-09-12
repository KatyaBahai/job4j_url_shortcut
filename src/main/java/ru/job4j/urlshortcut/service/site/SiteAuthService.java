package ru.job4j.urlshortcut.service.site;

import ru.job4j.urlshortcut.dto.authentication.request.RegisterRequestDto;
import ru.job4j.urlshortcut.dto.authentication.response.RegisterResponseDto;

public interface SiteAuthService {
    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
}
