package com.byteobject.prototype.springsecurity2.repository.person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    boolean exists(int id);

    Optional<PersonDO> getPersonById(int id);

    List<PersonDO> getAllPeople();

    PersonDO createPerson(PersonDO personDO);

    PersonDO updatePerson(PersonDO personDO);

    void deletePersonById(int id);

}
