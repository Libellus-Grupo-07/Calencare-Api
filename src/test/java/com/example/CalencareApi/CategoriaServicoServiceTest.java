package com.example.CalencareApi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoAtualizarDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoConsultaDto;
import com.example.CalencareApi.dto.categoria.servico.CategoriaServicoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaServico;
import com.example.CalencareApi.mapper.CategoriaServicoMapper;
import com.example.CalencareApi.repository.CategoriaServicoRepository;
import com.example.CalencareApi.service.CategoriaServicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoriaServicoServiceTest {
    @Test
    void contextLoads() {
    }

    /*@Mock
    private CategoriaServicoRepository categoriaServicoRepository;

    @InjectMocks
    private CategoriaServicoService categoriaServicoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Teste de cadastro de categoria de serviço")
    public void testCadastrar() {
        CategoriaServicoCriacaoDto novaCategoriaDto = new CategoriaServicoCriacaoDto();
        novaCategoriaDto.setNome("Nome da Categoria");
        novaCategoriaDto.setDescricao("Descrição da Categoria");

        CategoriaServico categoriaMock = CategoriaServicoMapper.toEntity(novaCategoriaDto);
        categoriaMock.setId(1);

        when(categoriaServicoRepository.save(any(CategoriaServico.class))).thenReturn(categoriaMock);

        CategoriaServicoConsultaDto result = categoriaServicoService.cadastrar(novaCategoriaDto);

        assertNotNull(result);
        assertEquals(novaCategoriaDto.getNome(), result.getNome());
        assertEquals(novaCategoriaDto.getDescricao(), result.getDescricao());
        verify(categoriaServicoRepository).save(any(CategoriaServico.class));
    }

    @Test
    @DisplayName("Teste de listagem de todas as categorias de serviço")
    public void testBuscarTodos() {
        List<CategoriaServico> categorias = new ArrayList<>();
        CategoriaServico categoria = new CategoriaServico();
        categoria.setNome("Nome da Categoria");
        categoria.setDescricao("Descrição da Categoria");
        categorias.add(categoria);

        when(categoriaServicoRepository.findAll()).thenReturn(categorias);

        List<CategoriaServicoConsultaDto> result = categoriaServicoService.buscarTodos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Nome da Categoria", result.get(0).getNome());
        verify(categoriaServicoRepository).findAll();
    }

    @Test
    @DisplayName("Teste de busca de categoria de serviço por ID")
    public void testBuscarPorId() {
        Integer id = 1;
        CategoriaServico categoria = new CategoriaServico();
        categoria.setId(id);
        categoria.setNome("Nome da Categoria");
        categoria.setDescricao("Descrição da Categoria");

        when(categoriaServicoRepository.findById(id)).thenReturn(Optional.of(categoria));

        CategoriaServicoConsultaDto result = categoriaServicoService.buscarPorId(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Nome da Categoria", result.getNome());
        verify(categoriaServicoRepository).findById(id);
    }

    @Test
    @DisplayName("Teste de atualização de categoria de serviço")
    public void testAlterar() {
        Integer id = 1;
        CategoriaServicoAtualizarDto categoriaDto = new CategoriaServicoAtualizarDto();
        categoriaDto.setNome("Nome Atualizado");
        categoriaDto.setDescricao("Descrição Atualizada");

        CategoriaServico categoria = new CategoriaServico();
        categoria.setId(id);
        categoria.setNome("Nome Antigo");
        categoria.setDescricao("Descrição Antiga");

        when(categoriaServicoRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaServicoRepository.save(any(CategoriaServico.class))).thenReturn(categoria);

        CategoriaServicoConsultaDto result = categoriaServicoService.alterar(id, categoriaDto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(categoriaDto.getNome(), result.getNome());
        assertEquals(categoriaDto.getDescricao(), result.getDescricao());
        verify(categoriaServicoRepository).findById(id);
        verify(categoriaServicoRepository).save(any(CategoriaServico.class));
    }

    @Test
    @DisplayName("Teste de deleção de categoria de serviço")
    public void testDeletar() {
        Integer id = 1;

        doNothing().when(categoriaServicoRepository).deleteById(id);

        categoriaServicoService.deletar(id);

        verify(categoriaServicoRepository).deleteById(id);
    }

     */
}
