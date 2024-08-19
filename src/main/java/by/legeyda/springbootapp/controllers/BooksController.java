package by.legeyda.springbootapp.controllers;



import by.legeyda.springbootapp.dao.BookDAO;
import by.legeyda.springbootapp.dao.PersonDAO;
import by.legeyda.springbootapp.models.Book;
import by.legeyda.springbootapp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;


    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showIndexPage(Model model) {
        model.addAttribute("books", bookDAO.getListOfBooksFromDB());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showInfoPage(@PathVariable("id") int id,
                               @ModelAttribute("person") Person person,
                               Model model) {
        model.addAttribute("book", bookDAO.getBookById(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.getListOfPeopleFromDB());
        return "books/info";
    }

    @GetMapping("/new")
    public String showNewPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping("/new")
    public String addNewBook(@ModelAttribute Book book) {
        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") int id,
                               Model model) {
        model.addAttribute("book", bookDAO.getBookById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") Book book) {
        book.setBookId(id);
        bookDAO.updateBook(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignUser(@PathVariable("id") int id,
                             @ModelAttribute("person") Person selectedPerson){
        bookDAO.assignBook(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books/";
    }

}
