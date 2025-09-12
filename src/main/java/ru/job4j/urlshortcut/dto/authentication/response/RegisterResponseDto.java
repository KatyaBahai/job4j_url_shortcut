package ru.job4j.urlshortcut.dto.authentication.response;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDto {
    private boolean registration;
    private String login;
    private String password;
}
