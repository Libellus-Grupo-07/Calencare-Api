package com.example.CalencareApi.dto.agendamento;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AgendamentoDashSemanaValorDto {
    private Integer semana;
    private Double valor;

    public AgendamentoDashSemanaValorDto(Integer semana, Double valor) {
        this.semana = semana;
        this.valor = valor;
    }
}
