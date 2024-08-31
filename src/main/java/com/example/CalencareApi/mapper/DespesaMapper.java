package com.example.CalencareApi.mapper;

import com.example.CalencareApi.dto.despesa.DespesaConsultaDto;
import com.example.CalencareApi.dto.despesa.DespesaCriacaoDto;
import com.example.CalencareApi.entity.Despesa;

import java.util.List;

public class DespesaMapper {

    public static Despesa toEntity(DespesaCriacaoDto despesaDto) {
        Despesa despesaEntity = new Despesa();
        despesaEntity.setNome(despesaDto.getNome());
        despesaEntity.setDescricao(despesaDto.getDescricao());
        despesaEntity.setValor(despesaDto.getValor());
        return despesaEntity;
    }

    public static DespesaConsultaDto toDto(Despesa despesaEntity) {
        DespesaConsultaDto despesaDto = new DespesaConsultaDto();
        despesaDto.setId(despesaEntity.getId());
        despesaDto.setNome(despesaEntity.getNome());
        despesaDto.setDescricao(despesaEntity.getDescricao());
        despesaDto.setValor(despesaEntity.getValor());
        despesaDto.setDtCriacao(despesaEntity.getDtCriacao());
        despesaDto.setEmpresa(despesaEntity.getEmpresa());
        despesaDto.setCategoriaDespesa(despesaEntity.getCategoriaDespesa());
        return despesaDto;
    }

    public static List<DespesaConsultaDto> toDto(List<Despesa> despesas) {
        return despesas.stream().map(DespesaMapper::toDto).toList();
    }
}