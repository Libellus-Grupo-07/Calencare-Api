package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.despesa.DespesaAtualizarDto;
import com.example.CalencareApi.dto.despesa.DespesaConsultaDto;
import com.example.CalencareApi.entity.CategoriaDespesa;
import com.example.CalencareApi.entity.Despesa;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.mapper.DespesaMapper;
import com.example.CalencareApi.repository.CategoriaDespesaRepository;
import com.example.CalencareApi.repository.DespesaRepository;
import com.example.CalencareApi.repository.EmpresaRepository;
import com.example.CalencareApi.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private CategoriaDespesaRepository categoriaDespesaRepository;

    @PostMapping("/{empresaId}/{categoriaId}")
    public ResponseEntity<Despesa> cadastrarDespesa(
            @Valid @RequestBody Despesa novaDespesa,
            @PathVariable int empresaId,
            @PathVariable int categoriaId) {
        Optional<Empresa> empresa = empresaRepository.findById(empresaId);
        Optional<CategoriaDespesa> categoriaDespesa = categoriaDespesaRepository.findById(categoriaId);
        if(empresa.isEmpty() || categoriaDespesa.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        if(Objects.isNull(novaDespesa)){
            return ResponseEntity.status(400).build();
        }
        //Despesa despesa = DespesaMapper.toEntity(novaDespesa);
        novaDespesa.setEmpresa(empresa.get());
        novaDespesa.setCategoriaDespesa(categoriaDespesa.get());
        novaDespesa.setBitStatus(1);
        novaDespesa.setDtCriacao(LocalDateTime.now());
        Despesa despesaSave = despesaRepository.save(novaDespesa);
        DespesaConsultaDto despesaCadastrada = DespesaMapper.toDto(despesaSave);
        return ResponseEntity.status(201).body(despesaSave);
    }

    @GetMapping
    public ResponseEntity<List<DespesaConsultaDto>> listarDespesa() {
        List<DespesaConsultaDto> despesas = this.despesaService.listar();
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(despesas);
    }

    @GetMapping("/empresa/{id}")
    public ResponseEntity<List<DespesaConsultaDto>> listarDespesaPorEmpresaId(@PathVariable Integer id) {
        List<DespesaConsultaDto> despesas = this.despesaService.listarPorEmpresaId(id);
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(despesas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaConsultaDto> buscarDespesaPorId(@PathVariable Integer id) {
        if (!this.despesaService.existePorId(id)) {
            return ResponseEntity.status(404).build();
        }
        DespesaConsultaDto despesa = this.despesaService.buscarPorId(id);
        return ResponseEntity.status(200).body(despesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaConsultaDto> atualizarDespesaPorId(@PathVariable Integer id, @Valid @RequestBody DespesaAtualizarDto despesaDto) {
        if (Objects.isNull(despesaDto)) {
            return ResponseEntity.status(400).build();
        }
        if (!this.despesaService.existePorId(id)) {
            return ResponseEntity.status(404).build();
        }
        DespesaConsultaDto despesaAtualizada = this.despesaService.atualizar(id, despesaDto);
        return ResponseEntity.status(200).body(despesaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDespesa(@PathVariable Integer id) {
        if (!this.despesaService.existePorId(id)) {
            return ResponseEntity.status(404).build();
        }
        this.despesaService.excluirPorId(id);
        return ResponseEntity.status(200).build();
    }


}
