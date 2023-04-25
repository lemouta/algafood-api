package net.mouta.algafood.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.mouta.algafood.domain.model.Pedido;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

    private Pedido pedido;

}
