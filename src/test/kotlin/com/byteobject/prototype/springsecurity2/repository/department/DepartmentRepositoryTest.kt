package com.byteobject.prototype.springsecurity2.repository.department

import com.byteobject.prototype.springsecurity2.repository.person.PersonDO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
class DepartmentRepositoryTest @Autowired constructor(private val departmentRepository: DepartmentRepository) {

    @Test
    fun testGetNonExistentDepartment() {
        val departmentDO = departmentRepository.getDepartmentById(100)
        assertTrue { departmentDO.isEmpty }
    }

    @Test
    fun testGetAllDepartment() {
        var department1 = DepartmentDO()
        department1.name = "department1"
        department1.description = "department desc"

        var department2 = DepartmentDO()
        department2.name = "department2"
        department2.description = "department desc"

        department1 = departmentRepository.createDepartment(department1)
        department2 = departmentRepository.createDepartment(department2)

        var allDepartment = departmentRepository.allDepartments
        assertEquals(2, allDepartment.size)

        departmentRepository.deleteDepartmentById(department1.id)
        allDepartment = departmentRepository.allDepartments
        assertEquals(1, allDepartment.size)

        departmentRepository.deleteDepartmentById(department2.id)
        allDepartment = departmentRepository.allDepartments
        assertTrue { allDepartment.isEmpty() }
    }

    @Test
    fun testCreateDepartment() {
        val departmentDO = DepartmentDO()
        departmentDO.description = "department desc"
        departmentDO.name = "department1"

        val created = departmentRepository.createDepartment(departmentDO)
        assertEquals(departmentDO.name, created.name)
        assertEquals(departmentDO.description, created.description)

        val retrieved = departmentRepository.getDepartmentById(created.id)
        assertTrue { retrieved.isPresent }
        assertEquals(departmentDO.description, retrieved.get().description)
        assertEquals(departmentDO.name, retrieved.get().name)

        departmentRepository.deleteDepartmentById(created.id)
    }

    @Test
    fun testUpdateDepartment() {
        val departmentDO = DepartmentDO()
        departmentDO.description = "department"
        departmentDO.name = "department1"

        val created = departmentRepository.createDepartment(departmentDO)
        assertEquals(departmentDO.name, created.name)
        assertEquals(departmentDO.description, created.description)
        assertTrue { created.id > 0 }

        created.name = "updated name"
        created.description = "updated desc"

        val updated = departmentRepository.updateDepartment(created)
        assertEquals(created.name, updated.name)
        assertEquals(created.description, updated.description)
        assertEquals(created.id, updated.id)

        departmentRepository.deleteDepartmentById(created.id)
    }

    @Test
    fun testDeleteDepartment() {
        val departmentDO = DepartmentDO()
        departmentDO.description = "department"
        departmentDO.name = "department1"

        val created = departmentRepository.createDepartment(departmentDO)
        var retrieved = departmentRepository.getDepartmentById(created.id)
        assertTrue { retrieved.isPresent }
        departmentRepository.deleteDepartmentById(created.id)

        retrieved = departmentRepository.getDepartmentById(created.id)
        assertTrue { retrieved.isEmpty }
    }

}