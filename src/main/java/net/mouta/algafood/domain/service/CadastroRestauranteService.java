package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.mouta.algafood.domain.exception.RestauranteNaoEncontradoException;
import net.mouta.algafood.domain.repository.RestauranteRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	public Restaurante buscarOuFalhar(Long id) {
	    return restauranteRepository.findById(id)
	        .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
				
		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.ativar();
	}

	@Transactional
	public void inativar(Long id) {
		Restaurante restaurante = buscarOuFalhar(id);
		restaurante.inativar();
	}

	@Transactional
	public void ativar(List<Long> ids) {
		ids.forEach(this::ativar);
	}

	@Transactional
	public void inativar(List<Long> ids) {
		ids.forEach(this::inativar);
	}

	@Transactional
	public void abrir(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		restaurante.adicionarResponsavel(usuario);
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		restaurante.removerResponsavel(usuario);
	}

}
