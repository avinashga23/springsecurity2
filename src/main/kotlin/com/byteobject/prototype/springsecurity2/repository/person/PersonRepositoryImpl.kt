package com.byteobject.prototype.springsecurity2.repository.person

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

@Repository("personRepository")
class PersonRepositoryImpl : PersonRepository {

    companion object {
        private val peopleMap = HashMap<Int, PersonDO>()
        private val LOGGER = LoggerFactory.getLogger(PersonRepositoryImpl::class.java)
        private var id = 0
    }

    override fun exists(id: Int): Boolean {
        LOGGER.info("exists $id")
        return peopleMap.containsKey(id)
    }

    override fun getPersonById(id: Int): Optional<PersonDO> {
        LOGGER.info("get person by id $id")
        return Optional.ofNullable(peopleMap[id])
    }

    override fun getAllPeople(): MutableList<PersonDO> {
        LOGGER.info("get all people")
        return peopleMap.values.stream().collect(Collectors.toList())
    }

    override fun createPerson(personDO: PersonDO?): PersonDO {
        LOGGER.info("create person")
        personDO!!
        personDO.id = ++id
        peopleMap[id] = personDO

        return personDO
    }

    override fun updatePerson(personDO: PersonDO?): PersonDO {
        LOGGER.info("update person")
        personDO!!
        peopleMap[personDO.id] = personDO

        return personDO
    }

    override fun deletePersonById(id: Int) {
        LOGGER.info("delete person")
        peopleMap.remove(id)
    }

}