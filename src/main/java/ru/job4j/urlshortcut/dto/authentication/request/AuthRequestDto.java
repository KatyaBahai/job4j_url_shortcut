package ru.job4j.urlshortcut.dto.authentication.request;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
