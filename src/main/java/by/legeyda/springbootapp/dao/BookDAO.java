package by.legeyda.springbootapp.dao;

import by.legeyda.springbootapp.models.Book;
import by.legeyda.springbootapp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getListOfBooksFromDB() {
        String sql = "Select * from book order by book_id";
        return jdbcTemplate.query(sql, new BookMapper());
    }

    public void saveBook(Book book) {
        String sql = "insert into book(title, author, year) values(?,?,?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void updateBook(Book book) {
        String sql = "UPDATE book set title=?, author=? ,year=? where book_id=?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getYear(), book.getBookId());
    }

    public Book getBookById(int id) {
        String sql = "select * from book where book_id =?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> getBookOwner(int id) {
        String sql = "select person.* from book\n" +
                "join person on person.person_id = book.person_id\n" +
                "where book_id=?";
        return jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void assignBook(int bookId, Person selectedPerson) {
        String sql = "update book\n" +
                "set person_id =?\n" +
                "where book_id=?";
        jdbcTemplate.update(sql, selectedPerson.getPersonId(), bookId);
    }

    public void releaseBook(int bookId) {
        String sql = "update book\n" +
                "set person_id = null\n" +
                "where book_id=?";
        jdbcTemplate.update(sql, bookId);
    }

    public void deleteBook(int bookId){
        String sql = "delete from book where book_id=?";
        jdbcTemplate.update(sql, bookId);
    }

}
