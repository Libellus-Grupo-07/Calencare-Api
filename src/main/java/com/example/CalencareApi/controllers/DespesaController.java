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



}
