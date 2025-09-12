package ru.job4j.urlshortcut.dto.convert;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlConvertResponseDto {
    @NotBlank
    private String transformedUrl;

}
