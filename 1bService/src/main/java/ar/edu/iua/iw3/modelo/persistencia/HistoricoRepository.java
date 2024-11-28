package ar.edu.iua.iw3.modelo.persistencia;

import java.util.Optional;

import ar.edu.iua.iw3.modelo.Historico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HistoricoRepository extends PagingAndSortingRepository<Historico, Long>{

	Page findAll(Pageable pageable);

	@Query(value = "select * from historico  where identificador=? order by fecha_hora_recepcion desc limit 1;", nativeQuery = true)
	Optional<Historico> findLast(String identificador);

}
