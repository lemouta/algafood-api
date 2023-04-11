package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Transactional
	public void confirmar(String codigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
		pedido.confirmar();
	}

	@Transactional
	public void entregar(String codigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
		pedido.entregar();
	}

	@Transactional
	public void cancelar(String codigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
		pedido.cancelar();
	}

}
