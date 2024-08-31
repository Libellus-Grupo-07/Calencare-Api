package com.example.CalencareApi.dto.despesa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DespesaAtualizarDto {
    private Integer id;
    private String nome;
    private String descricao;
    private Double valor;
}