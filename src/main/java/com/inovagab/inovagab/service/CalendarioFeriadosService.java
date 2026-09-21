package com.inovagab.inovagab.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inovagab.inovagab.dto.FeriadoResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarioFeriadosService {
    private final RestClient brasilApi = RestClient.create("https://brasilapi.com.br/api");

    public Optional<FeriadoResponse> proximoFeriadoNacional() {
        LocalDate hoje = LocalDate.now();
        try {
            List<FeriadoBrasilApi> feriados = brasilApi.get()
                    .uri("/feriados/v1/{ano}", hoje.getYear())
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<FeriadoBrasilApi>>() {});

            if (feriados == null) {
                return Optional.empty();
            }

            return feriados.stream()
                    .filter(feriado -> feriado.date() != null && !LocalDate.parse(feriado.date()).isBefore(hoje))
                    .min(java.util.Comparator.comparing(feriado -> LocalDate.parse(feriado.date())))
                    .map(feriado -> new FeriadoResponse(feriado.date(), feriado.name(), feriado.type()));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record FeriadoBrasilApi(String date, String name, String type) {
    }
}
