package net.mouta.algafood.api.assembler;

import net.mouta.algafood.api.model.input.CidadeInput;
import net.mouta.algafood.domain.model.Cidade;
import net.mouta.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainModel(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
        // para evitar exceção quando atualizar a cozinha do restaurante
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
}
