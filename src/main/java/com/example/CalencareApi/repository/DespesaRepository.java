package com.example.CalencareApi.repository;

import com.example.CalencareApi.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
    Boolean existsDespesaByNome(String nome);

    Optional<List<Despesa>> findByEmpresaId (Integer id);
}
