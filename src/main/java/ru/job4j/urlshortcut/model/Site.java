package ru.job4j.urlshortcut.model;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Entity
@Table(name = "sites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Site {

    @Schema(description = "Model Information about a site that gets registered in the program as a user")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "domain_name", unique = true, nullable = false)
    private String domainName;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;
}
