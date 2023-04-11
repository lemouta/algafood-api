package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.exception.PermissaoNaoEncontradaException;
import net.mouta.algafood.domain.model.Permissao;
import net.mouta.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;

	public Permissao buscarOuFalhar(Long id) {
		return permissaoRepository.findById(id)
			.orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}

}
