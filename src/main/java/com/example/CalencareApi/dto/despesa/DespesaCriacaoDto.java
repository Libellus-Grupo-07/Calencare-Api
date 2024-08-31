package com.example.CalencareApi.dto.despesa;

import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.entity.Empresa;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DespesaCriacaoDto {
    @NotBlank
    private String nome;
    @Size(min = 5, max = 255)
    private String descricao;
    @NotNull
    private Double valor;
    @NotNull
    private LocalDateTime dtCriacao;
    @NotNull
    private Integer bitStatus;
    @NotNull
    private Empresa empresa;
    @NotNull
    private CategoriaDespesa categoriaDespesa;
}