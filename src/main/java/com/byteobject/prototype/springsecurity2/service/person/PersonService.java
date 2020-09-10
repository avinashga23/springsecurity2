package com.byteobject.prototype.springsecurity2.service.person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    boolean exists(int id);

    Optional<PersonBO> getPersonById(int id);

    List<PersonBO> getAllPeople();

    PersonBO createPerson(PersonBO personBO);

    PersonBO updatePerson(PersonBO personBO);

    void deletePersonById(int id);

}
