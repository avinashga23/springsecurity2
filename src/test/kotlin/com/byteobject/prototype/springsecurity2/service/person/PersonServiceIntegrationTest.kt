package com.byteobject.prototype.springsecurity2.service.person

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class PersonServiceIntegrationTest @Autowired constructor(private val personService: PersonService) {

    @Test
    fun testGetPersonByNonExistingId() {
        val empty = personService.getPersonById(100)
        assertTrue { empty.isEmpty }
    }

    @Test
    fun testGetPersonById() {
        val personBO1 = constructPersonBO(1)
        val created = personService.createPerson(personBO1)
        val present = personService.getPersonById(created.id)
        assertTrue { present.isPresent }
        assertNotNull(present.get())
        assertEquals(created.id, present.get().id)
        assertEquals(created.name, present.get().name)
        assertEquals(created.email, present.get().email)
        personService.deletePersonById(created.id)
    }

    @Test
    fun testCreatePerson() {
        val personBO1 = constructPersonBO(1)
        val created = personService.createPerson(personBO1)
        assertNotNull(created)
        val retrieved = personService.getPersonById(created.id)
        assertNotNull(retrieved.get())
        assertTrue { retrieved.isPresent }
        assertEquals(created.id, retrieved.get().id)
        assertEquals(created.name, retrieved.get().name)
        assertEquals(created.email, retrieved.get().email)
        personService.deletePersonById(created.id)
    }

    @Test
    fun testUpdateExistingPerson() {
        val personBO1 = constructPersonBO(1)
        val created = personService.createPerson(personBO1)
        assertNotNull(created)
        created.name = "updated name"
        val updated = personService.updatePerson(created)
        assertEquals(created.id, updated.id)
        assertEquals(created.name, updated.name)
        assertEquals(created.email, updated.email)
        personService.deletePersonById(created.id)
    }

    @Test
    fun testUpdateNonExistingPerson() {
        val personBO1 = constructPersonBO(100)
        assertThrows<PersonNotFoundException> { personService.updatePerson(personBO1) }
    }

    @Test
    fun testDeleteExistingPerson() {
        val personBO1 = constructPersonBO(1)
        val created = personService.createPerson(personBO1)
        assertTrue { personService.getPersonById(created.id).isPresent }
        assertDoesNotThrow { personService.deletePersonById(created.id) }
        assertTrue { personService.getPersonById(created.id).isEmpty }
    }

    @Test
    fun testDeleteNonExistingPerson() {
        assertDoesNotThrow { personService.deletePersonById(100) }
    }

    private fun constructPersonBO(index: Int): PersonBO {
        val personBO = PersonBO()
        personBO.id = index
        personBO.name = "person$index"
        personBO.email = "person$index@people.com"
        return personBO
    }

}