package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.model.Pedido;
import net.mouta.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public void confirmar(String codigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
		pedido.confirmar();
		pedidoRepository.save(pedido);
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
		pedidoRepository.save(pedido);
	}

}
