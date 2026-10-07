package org.example.backend.persistance;

import org.example.backend.user.Person;
import java.util.Optional;

public interface PersonDAO {
    Optional<Person> findPersonByEmail(String email);
    Person savePerson(Person person);
}
