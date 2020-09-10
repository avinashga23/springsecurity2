package com.byteobject.prototype.springsecurity2.controller.person;

import com.byteobject.prototype.springsecurity2.service.person.PersonBO;
import com.byteobject.prototype.springsecurity2.service.person.PersonNotFoundException;
import com.byteobject.prototype.springsecurity2.service.person.PersonService;
import com.github.dozermapper.core.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController("personController")
@RequestMapping("/api/v1/person")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    private final Mapper mapper;

    @Autowired
    public PersonController(PersonService personService, Mapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:read_write')")
    public PersonDTO getPersonById(@PathVariable("id") int id) {
        LOGGER.info("get person by id {}", id);
        Optional<PersonBO> personBO = personService.getPersonById(id);
        if (personBO.isEmpty())
            throw new PersonNotFoundException(PersonNotFoundException.PERSON_BY_ID_NOT_FOUND, Arrays.asList(id));
        return personBO.map(p -> mapper.map(p, PersonDTO.class)).get();
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('user:read', 'user:read_write')")
    public List<PersonDTO> getAllPeople() {
        LOGGER.info("get all people");
        return personService.getAllPeople().stream().map(p -> mapper.map(p, PersonDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('user:read_write')")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO, UriComponentsBuilder uriComponentsBuilder) {
        LOGGER.info("create person");
        PersonDTO created = mapper.map(personService.createPerson(mapper.map(personDTO, PersonBO.class)), PersonDTO.class);
        var createdUri = uriComponentsBuilder.path("/api/v1/person/{id}").buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(createdUri).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read_write')")
    public PersonDTO updatePerson(@PathVariable("id") int id, @RequestBody PersonDTO personDTO) {
        LOGGER.info("update person");
        personDTO.setId(id);
        return mapper.map(personService.updatePerson(mapper.map(personDTO, PersonBO.class)), PersonDTO.class);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read_write')")
    public void deletePerson(@PathVariable("id") int id) {
        LOGGER.info("delete person");
        personService.deletePersonById(id);
    }

}
