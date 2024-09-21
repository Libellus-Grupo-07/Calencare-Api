package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descricao;
    private String marca;
    @ManyToOne
    private CategoriaProduto categoriaProduto;
    @ManyToOne
    private Empresa empresa;
}
