package com.example.CalencareApi.service;

import com.example.CalencareApi.dto.produto.ProdutoAtualizarDto;
import com.example.CalencareApi.dto.produto.ProdutoConsultaDto;
import com.example.CalencareApi.dto.produto.ProdutoCriacaoDto;
import com.example.CalencareApi.entity.CategoriaProduto;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.Produto;
import com.example.CalencareApi.mapper.ProdutoMapper;
import com.example.CalencareApi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired private ProdutoRepository produtoRepository;
    @Autowired private EmpresaService empresaService;
    @Autowired private CategoriaProdutoService categoriaProdutoService;

    public ProdutoConsultaDto cadastrar(
            ProdutoCriacaoDto dto,
            Integer categoria,
            Integer idEmpresa) {
        if (dto == null) {
            return null;
        }
        if (empresaService.buscarEmpresaPorId(idEmpresa) == null) {
            return null;
        }
        if (categoriaProdutoService.buscarPorId(categoria) == null) {
            return null;
        }
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        CategoriaProduto categoriaProduto = categoriaProdutoService.buscarEntidadePorId(categoria);
        Produto produto = ProdutoMapper.toEntity(dto);
        produto.setEmpresa(empresa);
        produto.setCategoriaProduto(categoriaProduto);
        Produto produtoCadastrado = this.produtoRepository.save(produto);
        return ProdutoMapper.toDto(produtoCadastrado);
    }

    public ProdutoConsultaDto buscarPorIdPorEmpresa(Integer id, Integer idEmpresa) {
        Produto produto = this.produtoRepository.findByIdAndEmpresaId(id, idEmpresa);
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        if (produto == null) {
            return null;
        }
        if (!produto.getEmpresa().equals(empresa)) {
            return null;
        }
        return ProdutoMapper.toDto(produto);
    }

    private Produto buscarEntidadePorId(Integer id) {
        return this.produtoRepository.findById(id).orElse(null);
    }


    public ProdutoConsultaDto atualizar(ProdutoAtualizarDto dto,
                                        Integer idProduto,
                                        Integer idEmpresa,
                                        Integer idCategoria) {
        Produto produtoAtualizacao = this.buscarEntidadePorId(idProduto);
        if (produtoAtualizacao == null) { return null; }
        if (!produtoAtualizacao.getEmpresa().getId().equals(idEmpresa)) { return null; }
        if (categoriaProdutoService.buscarEntidadePorId(idCategoria) == null) { return null; }

        CategoriaProduto categoriaProduto = categoriaProdutoService.buscarEntidadePorId(idCategoria);
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);

        produtoAtualizacao.setNome(dto.getNome() == null ? produtoAtualizacao.getNome() : dto.getNome());
        produtoAtualizacao.setDescricao(dto.getDescricao() == null ? produtoAtualizacao.getDescricao() : dto.getDescricao());
        produtoAtualizacao.setMarca(dto.getMarca() == null ? produtoAtualizacao.getMarca() : dto.getMarca());
        produtoAtualizacao.setCategoriaProduto(categoriaProduto);
        produtoAtualizacao.setEmpresa(empresa);
        Produto produtoAtualizado = this.produtoRepository.save(produtoAtualizacao);
        return ProdutoMapper.toDto(produtoAtualizado);
    }

    public List<ProdutoConsultaDto> buscarPorNomeOuMarca(Integer idEmpresa, String nome) {
        return ProdutoMapper.toDto(this.produtoRepository.findByNomeOrMarca(idEmpresa, nome));
    }

    public List<ProdutoConsultaDto> listarPorEmpresaId(Integer idEmpresa) {
        return ProdutoMapper.toDto(this.produtoRepository.findAllByEmpresaId(idEmpresa));
    }

    public void deletarPorId(Integer id, Integer idEmpresa) {
        Produto produto = this.produtoRepository.findByIdAndEmpresaId(id, idEmpresa);
        if (produto == null) {
            return;
        }
        produto.setBitStatus(0);
        this.produtoRepository.save(produto);
    }


}
