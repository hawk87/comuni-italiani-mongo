package it.comuni.comuniitaliani.repository;

import it.comuni.comuniitaliani.model.Comune;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository extends ReactiveMongoRepository<Comune, Integer> {
}
