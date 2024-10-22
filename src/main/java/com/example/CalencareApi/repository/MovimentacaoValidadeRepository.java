package com.example.CalencareApi.repository;
import com.example.CalencareApi.entity.MovimentacaoValidade;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovimentacaoValidadeRepository extends JpaRepository<MovimentacaoValidade, Integer> {

    // Inativar todas as movimentações de estoque ao inativar/deletar uma validade
    @Modifying
    @Transactional
    @Query("UPDATE MovimentacaoValidade mv SET mv.bitStatus = 0 WHERE mv.validade.id = :id")
    void deleteByValidadeId(Integer id);

    @Query("SELECT mv FROM MovimentacaoValidade mv WHERE mv.validade.id = :id AND mv.bitStatus = 1 ORDER BY mv.dtCriacao DESC")
    List<MovimentacaoValidade> findByValidadeId(Integer id);

    @Query("SELECT mv FROM MovimentacaoValidade mv WHERE mv.id = :id AND mv.bitStatus = 1")
    <Optional>MovimentacaoValidade findByMovId(Integer id);
}
