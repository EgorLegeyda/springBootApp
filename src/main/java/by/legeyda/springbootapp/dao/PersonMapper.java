package by.legeyda.springbootapp.dao;


import by.legeyda.springbootapp.models.Person;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setPersonId(rs.getInt("person_id"));
        person.setName(rs.getString("name"));
        person.setBirthYear(rs.getInt("birth_year"));

        return person;
    }


}
