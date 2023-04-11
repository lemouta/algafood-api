package net.mouta.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.mouta.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

//	@Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
	List<Restaurante> findTop2ByNomeContaining(String nome);
	Optional<Restaurante> findFirstByNomeContaining(String nome);
	int countByCozinhaId(Long cozinhaId);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId") => META-INF/orm.xml
	List<Restaurante> consultarPorNome(String nome, Long cozinhaId);
	
}
