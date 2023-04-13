package net.mouta.algafood.api.controller;

import com.google.common.collect.ImmutableMap;
import net.mouta.algafood.api.assembler.PedidoInputDisassembler;
import net.mouta.algafood.api.assembler.PedidoModelAssembler;
import net.mouta.algafood.api.assembler.PedidoResumoModelAssembler;
import net.mouta.algafood.api.model.PedidoModel;
import net.mouta.algafood.api.model.PedidoResumoModel;
import net.mouta.algafood.api.model.input.PedidoInput;
import net.mouta.algafood.core.data.PageableTranslator;
import net.mouta.algafood.domain.exception.EntidadeNaoEncontradaException;
import net.mouta.algafood.domain.exception.NegocioException;
import net.mouta.algafood.domain.model.Pedido;
import net.mouta.algafood.domain.model.Usuario;
import net.mouta.algafood.domain.repository.PedidoRepository;
import net.mouta.algafood.domain.filter.PedidoFilter;
import net.mouta.algafood.domain.service.EmissaoPedidoService;
import net.mouta.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public Page<PedidoResumoModel> listar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
		pageable = traduzirPageable(pageable);
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent());
		return new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());
	}

	private Pageable traduzirPageable(Pageable pageable) {
		var mapeamento = ImmutableMap.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal"
		);
		return PageableTranslator.translate(pageable, mapeamento);
	}

//	@GetMapping
//	public List<PedidoResumoModel> listar() {
//		return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
//	}

//	@GetMapping
//	public MappingJacksonValue listar(PedidoFilter filtro, @RequestParam(required = false) String campos) {
//		List<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
//		List<PedidoResumoModel> pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//		if (StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//		}
//
//		pedidosWrapper.setFilters(filterProvider);
//
//		return pedidosWrapper;
//	}

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
