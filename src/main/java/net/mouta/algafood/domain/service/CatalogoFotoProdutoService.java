package net.mouta.algafood.domain.service;

import net.mouta.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import net.mouta.algafood.domain.model.FotoProduto;
import net.mouta.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getRestauranteId(), foto.getProduto().getId());

        String nomeArquivoExistente = null;
        if (fotoExistente.isPresent()) {
            produtoRepository.delete(foto);
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
        }

        foto.setNomeArquivo(fotoStorage.gerarNomeArquivo(foto.getNomeArquivo()));
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(foto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }

    @Transactional
    public void excluir(Long restauranteId, Long produtoId) {
        FotoProduto foto = buscarOuFalhar(restauranteId, produtoId);

        produtoRepository.delete(foto);
        produtoRepository.flush();

        fotoStorage.remover(foto.getNomeArquivo());
    }

}
