package net.mouta.algafood.api.controller;

import net.mouta.algafood.api.assembler.PedidoInputDisassembler;
import net.mouta.algafood.api.assembler.PedidoModelAssembler;
import net.mouta.algafood.api.assembler.PedidoResumoModelAssembler;
import net.mouta.algafood.api.model.PedidoModel;
import net.mouta.algafood.api.model.PedidoResumoModel;
import net.mouta.algafood.api.model.input.PedidoInput;
import net.mouta.algafood.domain.exception.EntidadeNaoEncontradaException;
import net.mouta.algafood.domain.exception.NegocioException;
import net.mouta.algafood.domain.model.Pedido;
import net.mouta.algafood.domain.model.Usuario;
import net.mouta.algafood.domain.repository.PedidoRepository;
import net.mouta.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private EmissaoPedidoService emissaoPedido;

	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;

	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;

	@GetMapping
	public List<PedidoResumoModel> listar() {
		return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
	}

	@GetMapping("/{codigo}")
	public PedidoModel buscar(@PathVariable String codigo) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
		return pedidoModelAssembler.toModel(pedido);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainModel(pedidoInput);

			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
