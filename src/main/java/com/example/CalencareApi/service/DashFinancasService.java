package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.agendamento.AgendamentoDashSemanaValorDto;
import com.example.CalencareApi.dto.despesa.DespesaDashSemanaValorDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashFinancasService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DespesaDashSemanaValorDto> getDespesaSemanal(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        String sql = """
                SELECT WEEKOFYEAR(DATE(d.dt_pagamento)) AS semana,
                    SUM(d.valor) AS valor
                    FROM DESPESA d
                    INNER JOIN empresa e ON e.id = d.empresa_id
                    WHERE e.id = :empresaId
                    AND DATE(d.dt_pagamento) BETWEEN :dataInicio AND :dataFim
                    AND d.bit_status = 1
                    GROUP BY WEEKOFYEAR(DATE(d.dt_pagamento))
                    ORDER BY semana";
                """;

        // Execute the native query and map the results
        List<DespesaDashSemanaValorDto> result = entityManager.createNativeQuery(sql, "DespesaDashSemanaValorDtoMapping")
                .setParameter("empresaId", empresaId)
                .setParameter("dataInicio", dataInicioTransformada)
                .setParameter("dataFim", dataFimTransformada)
                .getResultList();

        return result;
    }

    public List<AgendamentoDashSemanaValorDto> getAgendamentoValorSemanal(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        String sql = """
                SELECT SUM(sp.preco) valor, WEEKOFYEAR(DATE(a.dt_hora)) semana FROM agendamento a
                    INNER JOIN servico_preco sp ON sp.id = a.servico_preco_id
                    INNER JOIN empresa e ON e.id = sp.empresa_id
                    WHERE e.id = :empresaId
                    AND DATE(a.dt_hora) BETWEEN :dataInicio AND :dataFim
                    AND a.bit_status = 5
                    GROUP BY WEEKOFYEAR(DATE(a.dt_hora))
                    ORDER BY semana
                """;

        // Execute the native query and map the results
        List<AgendamentoDashSemanaValorDto> result = entityManager.createNativeQuery(sql, "AgendamentoDashSemanaValorDtoMapping")
                .setParameter("empresaId", empresaId)
                .setParameter("dataInicio", dataInicioTransformada)
                .setParameter("dataFim", dataFimTransformada)
                .getResultList();

        return result;
    }

    public List<AgendamentoDashSemanaValorDto> getAgendamentoLucroSemanal(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);

        String sql = """
                SELECT TRUNCATE(SUM(sp.preco * sp.comissao), 2) valor, WEEKOFYEAR(DATE(a.dt_hora)) semana FROM agendamento
                    INNER JOIN servico_preco sp ON sp.id = a.servico_preco_id
                    INNER JOIN empresa e ON e.id = sp.empresa_id
                    WHERE e.id = :empresaId
                    AND DATE(a.dt_hora) BETWEEN :dataInicio AND :dataFim
                    AND a.bit_status = 5
                    GROUP BY WEEKOFYEAR(DATE(a.dt_hora))
                    ORDER BY semana
                """;

        // Execute the native query and map the results
        List<AgendamentoDashSemanaValorDto> result = entityManager.createNativeQuery(sql, "AgendamentoDashSemanaValorDtoMapping")
                .setParameter("empresaId", empresaId)
                .setParameter("dataInicio", dataInicioTransformada)
                .setParameter("dataFim", dataFimTransformada)
                .getResultList();

        return result;
    }

    public List<Integer> buscarSemanasDoMes(Integer mes, Integer ano) {
        //LocalDate instance= LocalDate.now();
        List<Integer> semanas = new ArrayList<>();

        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano, mes, 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        LocalDateTime data = dataInicioTransformada;

        while (data.isBefore(dataFimTransformada)) {
            semanas.add(data.get(WeekFields.ISO.weekOfWeekBasedYear()));
            data = data.plusDays(7);
        }

        return semanas;
    }

    /*public Object[][] getDadosDashboard (Integer empresaId, Month mes, Year ano) {
        List<AgendamentoDashSemanaValorDto> agendamentosReceita = getAgendamentoValorSemanal(empresaId, mes, ano);
        List<AgendamentoDashSemanaValorDto> agendamentosLucro = getAgendamentoLucroSemanal(empresaId, mes, ano);
        List<DespesaDashSemanaValorDto> despesas = getDespesaSemanal(empresaId, mes, ano);


        return result;
    }*/
}
