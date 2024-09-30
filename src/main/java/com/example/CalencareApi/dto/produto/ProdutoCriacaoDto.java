package com.example.CalencareApi.dto.produto;

import com.example.CalencareApi.entity.CategoriaProduto;
import com.example.CalencareApi.entity.Empresa;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoCriacaoDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotBlank
    private String marca;
    @NotBlank
    private CategoriaProduto categoriaProduto;
    @NotBlank
    private Empresa empresa;
}
