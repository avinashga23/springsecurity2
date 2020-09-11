package com.byteobject.prototype.springsecurity2.service.person

import com.byteobject.prototype.springsecurity2.repository.person.PersonDO
import com.byteobject.prototype.springsecurity2.repository.person.PersonRepository
import com.github.dozermapper.core.Mapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class PersonServiceMockTest {

    @Test
    fun testGetPersonById() {
        val personRepository = Mockito.mock(PersonRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val personService = PersonServiceImpl(personRepository, mapper)

        val person1DO = constructPersonDO(1)
        val person1BO = constructPersonBO(1)

        Mockito.`when`(personRepository.getPersonById(1)).thenReturn(Optional.of(person1DO))
        Mockito.`when`(mapper.map(person1DO, PersonBO::class.java)).thenReturn(person1BO)

        val retrieved = personService.getPersonById(1)
        assertNotNull(retrieved)
        assertTrue { retrieved.isPresent }
        assertEquals(retrieved.get().id, person1BO.id)
        assertEquals(retrieved.get().name, person1BO.name)
        assertEquals(retrieved.get().email, person1BO.email)
    }

    @Test
    fun testGetAllPeople() {
        val personRepository = Mockito.mock(PersonRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val personService = PersonServiceImpl(personRepository, mapper)
        val personDO1 = constructPersonDO(1)
        val personDO2 = constructPersonDO(2)
        val personBO1 = constructPersonBO(1)
        val personBO2 = constructPersonBO(2)

        Mockito.`when`(personRepository.allPeople).thenReturn(mutableListOf(personDO1, personDO2))
        Mockito.`when`(mapper.map(personDO1, PersonBO::class.java)).thenReturn(personBO1)
        Mockito.`when`(mapper.map(personDO2, PersonBO::class.java)).thenReturn(personBO2)

        val allPeople = personService.allPeople
        assertEquals(2, allPeople.size)
        assertEquals(personBO1.id, allPeople[0].id)
        assertEquals(personBO1.name, allPeople[0].name)
        assertEquals(personBO1.email, allPeople[0].email)
        assertEquals(personBO2.id, allPeople[1].id)
        assertEquals(personBO2.name, allPeople[1].name)
        assertEquals(personBO2.email, allPeople[1].email)

    }

    @Test
    fun testCreatePerson() {
        val personRepository = Mockito.mock(PersonRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val personService = PersonServiceImpl(personRepository, mapper)

        val personDO1 = constructPersonDO(1)
        val personBO1 = constructPersonBO(1)

        Mockito.`when`(personRepository.createPerson(personDO1)).thenReturn(personDO1)
        Mockito.`when`(mapper.map(personBO1, PersonDO::class.java)).thenReturn(personDO1)
        Mockito.`when`(mapper.map(personDO1, PersonBO::class.java)).thenReturn(personBO1)

        val created = personService.createPerson(personBO1)
        assertNotNull(created)
        assertEquals(personBO1.name, created.name)
        assertEquals(personBO1.email, created.email)
        assertTrue { created.id > 0 }
    }

    @Test
    fun testUpdateExistingPerson() {
        val personRepository = Mockito.mock(PersonRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val personService = PersonServiceImpl(personRepository, mapper)

        val personDO1 = constructPersonDO(1)
        val personBO1 = constructPersonBO(1)

        Mockito.`when`(mapper.map(personBO1, PersonDO::class.java)).thenReturn(personDO1)
        Mockito.`when`(mapper.map(personDO1, PersonBO::class.java)).thenReturn(personBO1)
        Mockito.`when`(personRepository.updatePerson(personDO1)).thenReturn(personDO1)
        Mockito.`when`(personRepository.exists(1)).thenReturn(true)

        val updated = personService.updatePerson(personBO1)
        assertNotNull(updated)
        assertEquals(personBO1.id, updated.id)
        assertEquals(personBO1.name, updated.name)
        assertEquals(personBO1.email, updated.email)
    }

    @Test
    fun testUpdateNonExistingPerson() {
        val personRepository = Mockito.mock(PersonRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val personService = PersonServiceImpl(personRepository, mapper)

        val personBO1 = constructPersonBO(1)

        Mockito.`when`(personRepository.exists(1)).thenReturn(false)

        assertThrows<PersonNotFoundException> { personService.updatePerson(personBO1) }
    }

    @Test
    fun testDeletePerson() {
        val personRepository = Mockito.mock(PersonRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val personService = PersonServiceImpl(personRepository, mapper)

        Mockito.`when`(personRepository.deletePersonById(1)).thenAnswer {  }
        assertDoesNotThrow { personService.deletePersonById(1) }
    }

    private fun constructPersonBO(index: Int): PersonBO {
        val personBO = PersonBO()
        personBO.id = index
        personBO.name = "person$index"
        personBO.email = "person$index@people.com"
        return personBO
    }

    private fun constructPersonDO(index: Int): PersonDO {
        val personDO = PersonDO()
        personDO.id = index
        personDO.name = "person$index"
        personDO.email = "person$index@people.com"
        return personDO
    }

}