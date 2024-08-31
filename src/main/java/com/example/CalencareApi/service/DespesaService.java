package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.despesa.DespesaAtualizarDto;
import com.example.CalencareApi.dto.despesa.DespesaConsultaDto;
import com.example.CalencareApi.dto.despesa.DespesaCriacaoDto;
import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.entity.Despesa;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.mapper.DespesaMapper;
import com.example.CalencareApi.repository.CategoriaDespesaRepository;
import com.example.CalencareApi.repository.DespesaRepository;
import com.example.CalencareApi.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final EmpresaRepository empresaRepository;
    private final CategoriaDespesaRepository categoriaDespesaRepository;

    public DespesaService(DespesaRepository despesaRepository, EmpresaRepository empresaRepository, CategoriaDespesaRepository categoriaDespesaRepository) {
        this.despesaRepository = despesaRepository;
        this.empresaRepository = empresaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
    }


    public DespesaConsultaDto cadastrar(DespesaCriacaoDto despesaCriacaoDto) {
        if (Objects.isNull(despesaCriacaoDto)) {
            return null;
        }
        Despesa despesa = DespesaMapper.toEntity(despesaCriacaoDto);
        Despesa despesaCadastrada = this.despesaRepository.save(despesa);
        DespesaConsultaDto dto = DespesaMapper.toDto(despesaCadastrada);
        return dto;
    }

    public DespesaConsultaDto buscarPorId(Integer id) {
        Optional<Despesa> despesaBusca = this.despesaRepository.findById(id);

        if (despesaBusca.isEmpty()) {
            return null;
        }
        DespesaConsultaDto dto = DespesaMapper.toDto(despesaBusca.get());
        return dto;
    }

    public List<DespesaConsultaDto> listar() {
        return DespesaMapper.toDto(this.despesaRepository.findAll());
    }

    public List<DespesaConsultaDto> listarPorEmpresaId(Integer empresaId) {
        return DespesaMapper.toDto(despesaRepository.findByEmpresaId(empresaId).orElse(null));
    }

    public Boolean excluirPorId(Integer id) {
        if (!this.despesaRepository.existsById(id)) {
            return false;
        }
        this.despesaRepository.deleteById(id);
        return true;
    }

    public DespesaConsultaDto atualizar(Integer id, DespesaAtualizarDto despesaDto) {
        Optional<Despesa> despesaAtualizacao = this.despesaRepository.findById(id);

        if (despesaAtualizacao.isEmpty()) {
            return null;
        }
        Despesa despesaAtualizada = this.despesaRepository.save(despesaAtualizacao.get());
        DespesaConsultaDto dto = DespesaMapper.toDto(despesaAtualizada);
        return dto;
    }



    public Boolean existePorId(Integer id) {
        return this.despesaRepository.existsById(id);
    }

    public Optional<Empresa> buscarEmpresaPorId(int empresaId) {
        return empresaRepository.findById(empresaId);
    }

    public Optional<CategoriaDespesa> buscarCategoriaPorId(int categoriaId) {
        return categoriaDespesaRepository.findById(categoriaId);
    }
}
