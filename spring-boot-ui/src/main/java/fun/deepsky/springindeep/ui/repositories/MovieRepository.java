package fun.deepsky.springindeep.ui.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fun.deepsky.springindeep.ui.domain.Movie;

@Repository
public interface MovieRepository extends GraphRepository<Movie>{

	Movie findByName(@Param("name") String name);
	
	@Query("MATCH (m:Movie) WHERE m.name =~('(?i).*'+{name}+'.*') RETURN m")
	Page<Movie> findByName(@Param("name") String name,Pageable pageable);
	
}
