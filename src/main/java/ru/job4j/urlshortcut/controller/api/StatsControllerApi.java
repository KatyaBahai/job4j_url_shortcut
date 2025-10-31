package ru.job4j.urlshortcut.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.job4j.urlshortcut.dto.statistics.StatsResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

@Tag(name = "StatsController", description = "Stats management APIs")
public interface StatsControllerApi {

    @Operation(
            summary = "Get statistics for all urls",
            description = "Get statistics for each url from a particular registered website, provides a number of each url calls",
            tags = { "statistics", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = StatsResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    ResponseEntity<List<StatsResponseDto>> getAllUrlsStatisticsBySite(Authentication authentication);

    @Operation(
            summary = "Get statistics for 1 url",
            description = "Get statistics for 1 url by code, provides a number of url calls",
            tags = { "statistics", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = StatsResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    ResponseEntity<StatsResponseDto> getOneUrlStats(Authentication authentication, String code);
}