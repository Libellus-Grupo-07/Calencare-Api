package com.example.CalencareApi.dto.servicoPreco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ServicoPrecoAtualizacaoPrecoDto {
    @NotNull
    @Positive
    private Double preco;
}
