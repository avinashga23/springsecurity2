package com.byteobject.prototype.springsecurity2.service.department

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class DepartmentServiceIntegrationTest @Autowired constructor(private val departmentService: DepartmentService) {

    @Test
    fun testGetDepartmentByNonExistingId() {
        val empty = departmentService.getDepartmentById(100)
        assertTrue { empty.isEmpty }
    }

    @Test
    fun testGetDepartmentById() {
        val departmentBO1 = constructDepartmentBO(1)
        val created = departmentService.createDepartment(departmentBO1)
        val present = departmentService.getDepartmentById(created.id)
        assertTrue { present.isPresent }
        assertNotNull(present.get())
        assertEquals(created.id, present.get().id)
        assertEquals(created.name, present.get().name)
        assertEquals(created.description, present.get().description)
        departmentService.deleteDepartmentById(created.id)
    }

    @Test
    fun testCreateDepartment() {
        val departmentBO1 = constructDepartmentBO(1)
        val created = departmentService.createDepartment(departmentBO1)
        assertNotNull(created)
        val retrieved = departmentService.getDepartmentById(created.id)
        assertNotNull(retrieved.get())
        assertTrue { retrieved.isPresent }
        assertEquals(created.id, retrieved.get().id)
        assertEquals(created.name, retrieved.get().name)
        assertEquals(created.description, retrieved.get().description)
        departmentService.deleteDepartmentById(created.id)
    }

    @Test
    fun testUpdateExistingDepartment() {
        val departmentBO1 = constructDepartmentBO(1)
        val created = departmentService.createDepartment(departmentBO1)
        assertNotNull(created)
        created.name = "updated name"
        val updated = departmentService.updateDepartment(created)
        assertEquals(created.id, updated.id)
        assertEquals(created.name, updated.name)
        assertEquals(created.description, updated.description)
        departmentService.deleteDepartmentById(created.id)
    }

    @Test
    fun testUpdateNonExistingDepartment() {
        val departmentBO1 = constructDepartmentBO(100)
        assertThrows<DepartmentNotFoundException> { departmentService.updateDepartment(departmentBO1) }
    }

    @Test
    fun testDeleteExistingDepartment() {
        val departmentBO1 = constructDepartmentBO(1)
        val created = departmentService.createDepartment(departmentBO1)
        assertTrue { departmentService.getDepartmentById(created.id).isPresent }
        assertDoesNotThrow { departmentService.deleteDepartmentById(created.id) }
        assertTrue { departmentService.getDepartmentById(created.id).isEmpty }
    }

    @Test
    fun testDeleteNonExistingDepartment() {
        assertDoesNotThrow { departmentService.deleteDepartmentById(100) }
    }

    private fun constructDepartmentBO(index: Int): DepartmentBO {
        val departmentBO = DepartmentBO()
        departmentBO.id = index
        departmentBO.name = "department$index"
        departmentBO.description = "department$index description"
        return departmentBO
    }

}