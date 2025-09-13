package ru.job4j.urlshortcut.dto.convert;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlConvertRequestDto {
    @NotBlank
    private String urlToTransform;

}
