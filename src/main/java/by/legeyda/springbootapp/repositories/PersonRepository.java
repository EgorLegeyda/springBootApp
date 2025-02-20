package by.legeyda.springbootapp.repositories;

import by.legeyda.springbootapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
