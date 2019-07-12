package hello;

import org.springframework.data.repository.CrudRepository;

public interface SecondHandRepository extends CrudRepository<SecondHand, Integer> {
}
