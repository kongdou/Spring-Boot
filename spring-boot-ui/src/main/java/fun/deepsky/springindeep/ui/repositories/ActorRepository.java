package fun.deepsky.springindeep.ui.repositories;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import fun.deepsky.springindeep.ui.domain.Actor;

@Repository
public interface ActorRepository extends GraphRepository<Actor>{

}
