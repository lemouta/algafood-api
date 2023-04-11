package net.mouta.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import net.mouta.algafood.domain.exception.EntidadeEmUsoException;
import net.mouta.algafood.domain.exception.CidadeNaoEncontradaException;
import net.mouta.algafood.domain.model.Cidade;
import net.mouta.algafood.domain.model.Estado;
import net.mouta.algafood.domain.repository.CidadeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	public Cidade buscarOuFalhar(Long id) {
	    return cidadeRepository.findById(id)
	        .orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}

	@Transactional
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
                
        cidade.setEstado(estado);
                
		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}		
	}
}
