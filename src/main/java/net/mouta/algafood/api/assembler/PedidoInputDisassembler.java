package net.mouta.algafood.api.assembler;

import net.mouta.algafood.api.model.input.FormaPagamentoInput;
import net.mouta.algafood.api.model.input.PedidoInput;
import net.mouta.algafood.domain.model.FormaPagamento;
import net.mouta.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainModel(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido) {
        modelMapper.map(pedidoInput, pedido);
    }

}
