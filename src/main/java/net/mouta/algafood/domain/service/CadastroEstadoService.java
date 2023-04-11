package net.mouta.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import net.mouta.algafood.domain.exception.EntidadeEmUsoException;
import net.mouta.algafood.domain.exception.EstadoNaoEncontradoException;
import net.mouta.algafood.domain.model.Estado;
import net.mouta.algafood.domain.repository.EstadoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_EM_USO  = 
	        "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado buscarOuFalhar(Long id) {
	    return estadoRepository.findById(id)
	        .orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			estadoRepository.deleteById(id);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
		}		
	}

}
