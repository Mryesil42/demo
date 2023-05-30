package ch.zhaw.springboot.demo;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

@RestController
public class PersonController {

    private Map<Integer, Person> persons = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        this.persons.put(1,new Person(1, "Barack Obama"));
        this.persons.put(2,new Person(2, "Donald Trump"));
        this.persons.put(3,new Person(3, "Joe Biden"));
        System.out.println("Init Data");
    }

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable Integer id) {
        return this.persons.get(id);
    }

    @PostMapping("/person")
    public void createPerson(@RequestBody Person todo) {
        var newId = this.persons.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1;
        todo.setId(newId);
        this.persons.put(newId, todo);
    }

    @PutMapping("/person/{id}")
    public void updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        person.setId(id);
        this.persons.put(id, person);
    }

    @DeleteMapping("/person/{id}")
    public Person deletePerson(@PathVariable Integer id) {
        return this.persons.remove(id);
    }
}
