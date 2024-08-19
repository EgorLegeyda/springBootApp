package by.legeyda.springbootapp.services;


import by.legeyda.springbootapp.models.Book;
import by.legeyda.springbootapp.models.Person;
import by.legeyda.springbootapp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;


    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        List<Person> people = personRepository.findAll(Sort.by("personId"));
        return people;
    }

    public List<Book> getBooksByPersonId(int id){
        List<Book> books = personRepository.findById(id).get().getBooks();
        return books;
    }

}
