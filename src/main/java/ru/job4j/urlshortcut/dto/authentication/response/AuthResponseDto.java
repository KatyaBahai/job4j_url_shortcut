package ru.job4j.urlshortcut.dto.authentication.response;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private Long siteId;
    private String domainName;

    public AuthResponseDto(String accessToken, Long siteId, String domainName) {
        this.token = accessToken;
        this.siteId = siteId;
        this.domainName = domainName;
    }
}
