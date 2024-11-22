package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.DashSemanaValorDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DashFinancasService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<DashSemanaValorDto> getDespesaSemanal(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);
        String sql = """
                SELECT WEEKOFYEAR(DATE(d.dt_pagamento)) AS semana, SUM(d.valor) AS valor FROM despesa d
                    INNER JOIN empresa e ON e.id = d.empresa_id
                    WHERE e.id = :empresaId
                    AND DATE(d.dt_pagamento) BETWEEN :dataInicio AND :dataFim
                    AND d.bit_status = 1
                    GROUP BY WEEKOFYEAR(DATE(d.dt_pagamento))
                    ORDER BY semana;
                """;

        List<DashSemanaValorDto> result = entityManager.createNativeQuery(sql, "DespesaDashSemanaValorDtoMapping")
                .setParameter("empresaId", empresaId)
                .setParameter("dataInicio", dataInicioTransformada)
                .setParameter("dataFim", dataFimTransformada)
                .getResultList();

        return result;
    }

    public List<DashSemanaValorDto> getAgendamentoValorSemanal(Integer empresaId, Month mes, Year ano) {
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

        List<DashSemanaValorDto> result = entityManager.createNativeQuery(sql, "AgendamentoDashSemanaValorDtoMapping")
                .setParameter("empresaId", empresaId)
                .setParameter("dataInicio", dataInicioTransformada)
                .setParameter("dataFim", dataFimTransformada)
                .getResultList();

        return result;
    }

    public List<DashSemanaValorDto> getAgendamentoLucroSemanal(Integer empresaId, Month mes, Year ano) {
        LocalDateTime dataInicioTransformada = LocalDateTime.of(ano.getValue(), mes.getValue(), 1, 0, 0);
        LocalDateTime dataFimTransformada = dataInicioTransformada.plusMonths(1).minusSeconds(1);

        String sql = """
                SELECT TRUNCATE(SUM(sp.preco * sp.comissao), 2) valor, WEEKOFYEAR(DATE(a.dt_hora)) semana FROM agendamento a
                    INNER JOIN servico_preco sp ON sp.id = a.servico_preco_id
                    INNER JOIN empresa e ON e.id = sp.empresa_id
                    WHERE e.id = :empresaId
                    AND DATE(a.dt_hora) BETWEEN :dataInicio AND :dataFim
                    AND a.bit_status = 5
                    GROUP BY WEEKOFYEAR(DATE(a.dt_hora))
                    ORDER BY semana
                """;

        List<DashSemanaValorDto> result = entityManager.createNativeQuery(sql, "AgendamentoDashSemanaValorDtoMapping")
                .setParameter("empresaId", empresaId)
                .setParameter("dataInicio", dataInicioTransformada)
                .setParameter("dataFim", dataFimTransformada)
                .getResultList();

        return result;
    }

    public List<Integer> buscarSemanasDoMes(Integer mes, Integer ano) {
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

    public void validarDadosDash (List<DashSemanaValorDto> lista, List<Integer> semanas) {
        if (lista.isEmpty()) {
            for (int i = 0; i < semanas.size(); i++) {
                Integer semanaAtual = semanas.get(i);
                DashSemanaValorDto dashSemanaValorDto = new DashSemanaValorDto();
                dashSemanaValorDto.setSemana(semanaAtual);
                dashSemanaValorDto.setValor(0.0);
                lista.add(dashSemanaValorDto);
            }
        } else {
            if (lista.size() != semanas.size()) {
                for (int i = 0; i < semanas.size(); i++) {
                    Integer semanaAtual = semanas.get(i);
                    Boolean isEncontrado = false;
                    for (int j = 0; j < lista.size(); j++) {
                        if (semanaAtual == lista.get(j).getSemana()) {
                            isEncontrado = true;
                            break;
                        }
                    }
                    if (!isEncontrado) {
                        DashSemanaValorDto dashSemanaValorDto = new DashSemanaValorDto();
                        dashSemanaValorDto.setSemana(semanaAtual);
                        dashSemanaValorDto.setValor(0.0);
                        lista.add(dashSemanaValorDto);
                    }
                }
                lista.sort(Comparator.comparing(DashSemanaValorDto::getSemana));
            }
        }
    }

    public DashSemanaValorDto[][] getDadosDashboard (Integer empresaId, Month mes, Year ano) {
        List<DashSemanaValorDto> agendamentosReceita = getAgendamentoValorSemanal(empresaId, mes, ano);
        List<DashSemanaValorDto> agendamentosLucro = getAgendamentoLucroSemanal(empresaId, mes, ano);
        List<DashSemanaValorDto> despesas = getDespesaSemanal(empresaId, mes, ano);
        List<Integer> semanas = buscarSemanasDoMes(mes.getValue(), ano.getValue());
        DashSemanaValorDto[][] result = new DashSemanaValorDto[3][semanas.size()];

        validarDadosDash(agendamentosReceita, semanas);
        validarDadosDash(agendamentosLucro, semanas);
        validarDadosDash(despesas, semanas);

        for (int i = 0; i < semanas.size(); i++) {
            result[0][i] = agendamentosReceita.get(i);
            result[1][i] = agendamentosLucro.get(i);
            result[2][i] = despesas.get(i);
        }

        return result;
    }
}
