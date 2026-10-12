package org.example.backend.persistance;

import org.example.backend.user.Person;
import org.example.backend.user.Project;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProjectDAO {
    Collection<Project> findProjectsAssignedToPerson(Person person);


    Project save(Project project);

    Optional<Project> get(UUID id);

//    Collection<>
}
