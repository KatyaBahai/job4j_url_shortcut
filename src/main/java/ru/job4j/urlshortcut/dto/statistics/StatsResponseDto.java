package ru.job4j.urlshortcut.dto.statistics;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponseDto {
    private String url;
    private Long total;
}
