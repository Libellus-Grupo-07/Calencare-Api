package com.example.CalencareApi.service;
import com.example.CalencareApi.dto.produto.ProdutoConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeConsultaDto;
import com.example.CalencareApi.dto.validade.movimentacao.MovimentacaoValidadeCriacaoDto;
import com.example.CalencareApi.entity.Empresa;
import com.example.CalencareApi.entity.MovimentacaoValidade;
import com.example.CalencareApi.entity.Produto;
import com.example.CalencareApi.entity.Validade;
import com.example.CalencareApi.mapper.MovimentacaoValidadeMapper;
import com.example.CalencareApi.mapper.ProdutoMapper;
import com.example.CalencareApi.repository.MovimentacaoValidadeRepository;
import com.example.CalencareApi.repository.ProdutoRepository;
import com.example.CalencareApi.repository.ValidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MovimentacaoValidadeService {

    @Autowired MovimentacaoValidadeRepository movimentacaoValidadeRepository;
    @Autowired ValidadeRepository validadeRepository;
    @Autowired ProdutoRepository produtoRepository;
    @Autowired ProdutoService produtoService;
    @Autowired EmpresaService empresaService;

    public MovimentacaoValidadeConsultaDto cadastrar(MovimentacaoValidadeCriacaoDto dto) {
        Validade validade = validadeRepository.findById(dto.getIdValidade()).orElseThrow();
        MovimentacaoValidade movimentacaoValidade = MovimentacaoValidadeMapper.toEntity(dto);
        movimentacaoValidade.setValidade(validade);
        Integer quantidade = retornarQuantidadePorValidade(dto.getIdValidade());

        if (quantidade == 0 && dto.getTipoMovimentacao() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível retirar um produto sem estoque");
        }

        if (quantidade + dto.getQuantidade() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque negativo inválido");
        }

        if (quantidade < dto.getQuantidade() && dto.getTipoMovimentacao() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível retirar mais do que o estoque");
        }

        if (movimentacaoValidade.getTipoMovimentacao() == 0) {
            movimentacaoValidade.setQuantidade(movimentacaoValidade.getQuantidade() * -1);
        }

        movimentacaoValidade = movimentacaoValidadeRepository.save(movimentacaoValidade);
        return MovimentacaoValidadeMapper.toDto(movimentacaoValidade);
    }

    public Integer retornarQuantidadePorValidade(Integer idValidade) {
        List<MovimentacaoValidade> movimentacaoValidade = movimentacaoValidadeRepository.findByValidadeId(idValidade);
        if (movimentacaoValidade == null) {
            return null;
        }
        Integer quantidade = 0;
        for (MovimentacaoValidade mv : movimentacaoValidade) {
            quantidade += mv.getQuantidade();
        }
        return quantidade;
    }

    public Integer retornarQuantidadeTotalProduto(Integer idProduto) {
        List<Validade> validades = validadeRepository.findByProdutoId(idProduto);
        if (validades == null) {
            return null;
        }
        Integer quantidade = 0;
        for (Validade validade : validades) {
            quantidade += retornarQuantidadePorValidade(validade.getId());
        }
        return quantidade;
    }

    public MovimentacaoValidadeConsultaDto buscarPorId(Integer id) {
        MovimentacaoValidade movimentacaoValidade = movimentacaoValidadeRepository.findByMovId(id);
        if (movimentacaoValidade == null) {
            return null;
        }
        return MovimentacaoValidadeMapper.toDto(movimentacaoValidade);
    }

    public List<MovimentacaoValidadeConsultaDto> buscarPorValidadeId (Integer idValidade) {
        Validade validade = validadeRepository.findById(idValidade).orElse(null);
        if (validade == null) {
            return null;
        }
        List<MovimentacaoValidade> movimentacaoValidade = movimentacaoValidadeRepository.findByValidadeId(idValidade);
        return MovimentacaoValidadeMapper.toDto(movimentacaoValidade);
    }

    // PRODUTO
    public ProdutoConsultaDto buscarPorIdPorEmpresa(Integer id, Integer idEmpresa) {
        Produto produto = this.produtoRepository.findByIdAndEmpresaId(id, idEmpresa);
        Empresa empresa = empresaService.buscarEntidadePorId(idEmpresa);
        if (produto == null) {
            return null;
        }
        if (!produto.getEmpresa().equals(empresa)) {
            return null;
        }
        ProdutoConsultaDto produtoConsultaDto = ProdutoMapper.toDto(produto);
        produtoConsultaDto.setQntdTotalEstoque(retornarQuantidadeTotalProduto(id));
        return produtoConsultaDto;
    }

    public ProdutoConsultaDto buscarProdutoPorId(Integer id) {
        ProdutoConsultaDto produto = ProdutoMapper.toDto(produtoRepository.findById(id).orElse(null));
        if (produto == null) {
            return null;
        }
        Integer qntd = retornarQuantidadeTotalProduto(id);
        produto.setQntdTotalEstoque(qntd);
        return produto;
    }

    public List<ProdutoConsultaDto> buscarPorNomeOuMarca(Integer idEmpresa, String nome) {
        List<ProdutoConsultaDto> produtos = ProdutoMapper.toDto(this.produtoRepository.findByNomeOrMarca(idEmpresa, nome));
        for (ProdutoConsultaDto produto : produtos) {
            produto.setQntdTotalEstoque(retornarQuantidadeTotalProduto(produto.getId()));
        }
        return produtos;
    }

    public List<ProdutoConsultaDto> listarPorEmpresaId(Integer idEmpresa) {
        List<ProdutoConsultaDto> produtos = ProdutoMapper.toDto(this.produtoRepository.findAllByEmpresaId(idEmpresa));
        for (ProdutoConsultaDto produto : produtos) {
            produto.setQntdTotalEstoque(retornarQuantidadeTotalProduto(produto.getId()));
        }
        return produtos;
    }

    // KPIS
    /*public Double retornarMediaEstoqueProduto(Integer idProduto) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idProduto);
    }*/

    public List<Map<String,Object>> retornarQuantidadeMediaProdutos(Integer idEmpresa) {
        return movimentacaoValidadeRepository.findAverageByProdutoId(idEmpresa);
    }

    public Integer retornarQuantidadeProdutosAlta (Integer idEmpresa) {
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (media * 100) / qntdAtual;
            if (porcentagemRelativa > 30.0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosBaixa (Integer idEmpresa) {
        List<Map<String,Object>> mediaProdutos = retornarQuantidadeMediaProdutos(idEmpresa);
        Integer contador = 0;

        for (Map<String,Object> mediaProduto : mediaProdutos) {
            Integer idProduto = (Integer) mediaProduto.get("id_prod");
            Double media = (Double) mediaProduto.get("qntd");
            Integer qntdAtual = retornarQuantidadeTotalProduto(idProduto);
            Double porcentagemRelativa = (media * 100) / qntdAtual;
            if (porcentagemRelativa <= 30.0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosSemEstoque (Integer idEmpresa) {
        List<ProdutoConsultaDto> produtos =  listarPorEmpresaId(idEmpresa);
        Integer contador = 0;

        for (ProdutoConsultaDto produto : produtos) {
            Integer qntdAtual = retornarQuantidadeTotalProduto(produto.getId());
            if (qntdAtual == 0) {
                contador++;
            }
        }
        return contador;
    }

    public Integer retornarQuantidadeProdutosRepostosDia(Integer idEmpresa, LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = dataInicio.plusHours(23).plusMinutes(59).plusSeconds(59);
        Integer contador = movimentacaoValidadeRepository.findReposicaoByData(idEmpresa, dataInicio, dataFim);
        return contador;
    }

    /*
     * ESTOQUE ALTO OK
     * SEM ESTOQUE OK
     * RESPOSTOS NO DIA OK
     * ESTOQUE BAIXO -> CONSIDERAR MÉDIA DE VALORES, CERCA DE 25% DESSA MÉDIA OK
     * ALERTA PARA VALIDADES PROXIMAS
     * AUTOMATICAMENTE DESATIVAR DATAS VENCIDAS
     * */
}
