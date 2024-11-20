package com.example.CalencareApi.dto.despesa;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DespesaDashSemanaValorDto {
    private Integer semana;
    private Double valor;

    public DespesaDashSemanaValorDto(Integer semana, Double valor) {
        this.semana = semana;
        this.valor = valor;
    }
}
