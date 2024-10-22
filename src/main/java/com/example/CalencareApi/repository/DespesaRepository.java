package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
    Boolean existsDespesaByNome(String nome);

    List<Despesa> findByEmpresaId (Integer id);

    @Query("SELECT d FROM Despesa d WHERE d.empresa.id = :id AND d.bitStatus = 1 AND d.dtPagamento BETWEEN :dataInicio AND :dataFim")
    List<Despesa> encontrarDespesasPeriodo(Integer id, LocalDateTime dataInicio, LocalDateTime dataFim);

}
