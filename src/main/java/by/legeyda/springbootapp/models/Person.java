package by.legeyda.springbootapp.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="person_id")
    private int personId;

    @Column(name = "name")
    private String name;

    @Column(name="birth_year")
    private int birthYear;


    @OneToMany(mappedBy = "person")
    private List<Book> books;


    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("personId=").append(personId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", birthYear=").append(birthYear);
        sb.append('}');
        return sb.toString();
    }
}
