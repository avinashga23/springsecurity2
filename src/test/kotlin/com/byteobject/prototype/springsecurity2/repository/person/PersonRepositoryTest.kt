package com.byteobject.prototype.springsecurity2.repository.person

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
class PersonRepositoryTest @Autowired constructor(private val personRepository: PersonRepository) {

    @Test
    fun testGetNonExistentPerson() {
        val personDO = personRepository.getPersonById(100)
        assertTrue { personDO.isEmpty }
    }

    @Test
    fun testGetAllPeople() {
        var person1 = PersonDO()
        person1.name = "person1"
        person1.email = "person1@people.com"

        var person2 = PersonDO()
        person2.name = "person2"
        person2.email = "person2@people.com"

        person1 = personRepository.createPerson(person1)
        person2 = personRepository.createPerson(person2)

        var allPeople = personRepository.allPeople
        assertEquals(2, allPeople.size)

        personRepository.deletePersonById(person1.id)
        allPeople = personRepository.allPeople
        assertEquals(1, allPeople.size)

        personRepository.deletePersonById(person2.id)
        allPeople = personRepository.allPeople
        assertTrue { allPeople.isEmpty() }
    }

    @Test
    fun testCreatePerson() {
        val personDO = PersonDO()
        personDO.email = "person@people.com"
        personDO.name = "person1"

        val created = personRepository.createPerson(personDO)
        assertEquals(personDO.name, created.name)
        assertEquals(personDO.email, created.email)

        val retrieved = personRepository.getPersonById(created.id)
        assertTrue { retrieved.isPresent }
        assertEquals(personDO.email, retrieved.get().email)
        assertEquals(personDO.name, retrieved.get().name)

        personRepository.deletePersonById(created.id)
    }

    @Test
    fun testUpdatePerson() {
        val personDO = PersonDO()
        personDO.email = "person@people.com"
        personDO.name = "person1"

        val created = personRepository.createPerson(personDO)
        assertEquals(personDO.name, created.name)
        assertEquals(personDO.email, created.email)
        assertTrue { created.id > 0 }

        created.name = "updated name"
        created.email = "updated email"

        val updated = personRepository.updatePerson(created)
        assertEquals(created.name, updated.name)
        assertEquals(created.email, updated.email)
        assertEquals(created.id, updated.id)

        personRepository.deletePersonById(created.id)
    }

    @Test
    fun testDeletePerson() {
        val personDO = PersonDO()
        personDO.email = "person@people.com"
        personDO.name = "person1"

        val created = personRepository.createPerson(personDO)
        var retrieved = personRepository.getPersonById(created.id)
        assertTrue { retrieved.isPresent }
        personRepository.deletePersonById(created.id)

        retrieved = personRepository.getPersonById(created.id)
        assertTrue { retrieved.isEmpty }
    }

}