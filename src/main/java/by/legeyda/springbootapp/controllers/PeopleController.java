package by.legeyda.springbootapp.controllers;


import by.legeyda.springbootapp.dao.BookDAO;
import by.legeyda.springbootapp.dao.PersonDAO;
import by.legeyda.springbootapp.models.Person;
import by.legeyda.springbootapp.repositories.PersonRepository;
import by.legeyda.springbootapp.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonService personService;


    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PersonService personService) {
        this.personDAO = personDAO;
        this.personService = personService;}


    @GetMapping
    public String showIndexPage(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showInfoPage(@PathVariable("id") int id,
                               Model model) {
        model.addAttribute("person", personDAO.getPersonFromDB(id));
        model.addAttribute("books", personDAO.getBooksByPersonId(id));
        return "people/info";
    }


    @GetMapping("/new")
    public String showNewPage(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable("id") int id,
                               Model model) {
        model.addAttribute("person", personDAO.getPersonFromDB(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("person") Person person,
                           @PathVariable("id") int id) {

        person.setPersonId(id);
        personDAO.updatePerson(person);
        return "redirect:/people";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }


}
