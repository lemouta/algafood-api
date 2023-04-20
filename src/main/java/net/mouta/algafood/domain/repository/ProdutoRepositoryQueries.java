package net.mouta.algafood.domain.repository;

import net.mouta.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);

	void delete(FotoProduto foto);

}