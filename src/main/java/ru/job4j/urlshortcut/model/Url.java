package ru.job4j.urlshortcut.model;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Entity
@Table(name = "called_urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Url {

    @Schema(description = "Model Information about urls sent and converted to short urls")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    @Column(name = "redirect_count", nullable = false)
    private Long redirectCount = 0L;
}
