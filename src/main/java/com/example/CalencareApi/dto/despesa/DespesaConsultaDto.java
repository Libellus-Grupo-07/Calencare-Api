package com.example.CalencareApi.dto.despesa;

import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.entity.Empresa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DespesaConsultaDto {
    private Integer id;
    private String nome;
    private String descricao;
    private Double valor;
    private LocalDateTime dtCriacao;
    private Empresa empresa;
    private CategoriaDespesa categoriaDespesa;
}
