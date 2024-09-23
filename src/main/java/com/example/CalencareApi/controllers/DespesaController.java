package com.example.CalencareApi.controllers;

import com.example.CalencareApi.dto.despesa.DespesaAtualizarDto;
import com.example.CalencareApi.dto.despesa.DespesaConsultaDto;
import com.example.CalencareApi.dto.despesa.DespesaCriacaoDto;
import com.example.CalencareApi.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService service;
    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @PostMapping("/{idEmpresa}/{idCategoria}")
    public ResponseEntity<DespesaConsultaDto> cadastrar(@Valid @RequestBody DespesaCriacaoDto dto,
                                                        @PathVariable Integer idEmpresa,
                                                        @PathVariable Integer idCategoria) {
        if (Objects.isNull(dto)) {
            return ResponseEntity.status(400).build();
        }
        DespesaConsultaDto savedDto = service.cadastrar(dto, idEmpresa, idCategoria);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping("/{idEmpresa}/{id}")
    public ResponseEntity<DespesaConsultaDto> buscarPorId(@PathVariable Integer idEmpresa,
                                                          @PathVariable Integer id) {
        DespesaConsultaDto dto = service.retornarPorIdPorEmpresa(id, idEmpresa);
        if (Objects.isNull(dto)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{idEmpresa}/{id}/{idCategoria}")
    public ResponseEntity<DespesaConsultaDto> atualizar(@PathVariable Integer idEmpresa,
                                                        @PathVariable Integer id,
                                                        @PathVariable Integer idCategoria,
                                                        @Valid @RequestBody DespesaAtualizarDto dto) {
        if (Objects.isNull(dto)) {
            return ResponseEntity.status(400).build();
        }
        DespesaConsultaDto updatedDto = service.atualizarPorEmpresa(id, dto, idEmpresa, idCategoria);
        if (Objects.isNull(updatedDto)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{idEmpresa}/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idEmpresa, @PathVariable Integer id) {
        service.deletarPorId(id, idEmpresa);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/info/{idEmpresa}/{data}")
    public ResponseEntity<List<DespesaConsultaDto>> listarDespesasDia(
            @PathVariable Integer idEmpresa,
            @PathVariable LocalDateTime data) {
        List<DespesaConsultaDto> despesas = service.exibirDespesasDia(idEmpresa, data);
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/info/{idEmpresa}/{mes}/{ano}")
    public ResponseEntity<List<DespesaConsultaDto>> listarDespesasMes(
            @PathVariable Integer idEmpresa,
            @PathVariable Month mes,
            @PathVariable Year ano) {
        List<DespesaConsultaDto> despesas = service.exibirDespesasMes(idEmpresa, mes, ano);
        if (despesas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/total/{idEmpresa}/{mes}/{ano}")
    public ResponseEntity<Double> calcularDespesaTotalMes(
            @PathVariable Integer idEmpresa,
            @PathVariable Month mes,
            @PathVariable Year ano) {
        Double total = service.calcularDespesaTotalMes(idEmpresa, mes, ano);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/total/{idEmpresa}/{data}")
    public ResponseEntity<Double> calcularDespesaTotalDia(
            @PathVariable Integer idEmpresa,
            @PathVariable LocalDateTime data) {
        Double total = service.calcularDespesaTotalDia(idEmpresa, data);
        return ResponseEntity.ok(total);
    }

}
