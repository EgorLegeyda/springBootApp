package by.legeyda.springbootapp.dao;



import by.legeyda.springbootapp.models.Book;
import by.legeyda.springbootapp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getListOfPeopleFromDB() {
        String sql = "Select * from person order by person_id";
        return jdbcTemplate.query(sql, new PersonMapper());
    }

    public List<Book> getBooksByPersonId(int id){
        String sql = "SELECT * FROM book where person_id=?";
        return jdbcTemplate.query(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Person getPersonFromDB(int id) {
        String sql = "select * from person where person_id=?";
        Person person = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        return person;
    }



    public void updatePerson(Person person) {
        String sql = "UPDATE person set name=?, birth_year=? where person_id=?";
        jdbcTemplate.update(sql, person.getName(), person.getBirthYear(), person.getPersonId());
    }

    public void save(Person person){
        String sql = "insert into person(name, birth_year)  values (?, ?)";
        jdbcTemplate.update(sql, person.getName(), person.getBirthYear());
    }

    public void delete(int id){
        String sql = "DELETE FROM person where person_id=?";
        jdbcTemplate.update(sql, id);
    }
}
