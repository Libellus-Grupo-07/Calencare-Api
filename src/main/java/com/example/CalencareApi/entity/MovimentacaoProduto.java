package com.example.CalencareApi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter
public class MovimentacaoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricaoOperacao;
    private Integer quantidade;
    private LocalDateTime dtCriacao;
    @ManyToOne
    private Produto produto;
}
