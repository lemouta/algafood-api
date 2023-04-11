package net.mouta.algafood.core.modelmapper;

import net.mouta.algafood.api.model.EnderecoModel;
import net.mouta.algafood.api.model.input.ItemPedidoInput;
import net.mouta.algafood.domain.model.Endereco;
import net.mouta.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

//        modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//            .addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        modelMapper.createTypeMap(Endereco.class, EnderecoModel.class)
                .<String>addMapping(
                        enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                        (enderecoModelDest, val) -> enderecoModelDest.getCidade().setEstado(val));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }

}
