package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.exception.EntidadeEmUsoException;
import net.mouta.algafood.domain.exception.EstadoNaoEncontradoException;
import net.mouta.algafood.domain.exception.ProdutoNaoEncontradoException;
import net.mouta.algafood.domain.model.Estado;
import net.mouta.algafood.domain.model.Produto;
import net.mouta.algafood.domain.repository.EstadoRepository;
import net.mouta.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto salvar(Produto produto) {
		return produtoRepository.save(produto);
	}

	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId)
			.orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
	}

}
