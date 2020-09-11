package com.byteobject.prototype.springsecurity2.service.department

import com.byteobject.prototype.springsecurity2.repository.department.DepartmentDO
import com.byteobject.prototype.springsecurity2.repository.department.DepartmentRepository
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
class DepartmentServiceMockTest {

    @Test
    fun testDepartmentPersonById() {
        val departmentRepository = Mockito.mock(DepartmentRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val departmentService = DepartmentServiceImpl(departmentRepository, mapper)

        val department1DO = constructDepartmentDO(1)
        val department1BO = constructDepartmentBO(1)

        Mockito.`when`(departmentRepository.getDepartmentById(1)).thenReturn(Optional.of(department1DO))
        Mockito.`when`(mapper.map(department1DO, DepartmentBO::class.java)).thenReturn(department1BO)

        val retrieved = departmentService.getDepartmentById(1)
        assertNotNull(retrieved)
        assertTrue { retrieved.isPresent }
        assertEquals(retrieved.get().id, department1BO.id)
        assertEquals(retrieved.get().name, department1BO.name)
        assertEquals(retrieved.get().description, department1BO.description)
    }

    @Test
    fun testGetAllDepartments() {
        val departmentRepository = Mockito.mock(DepartmentRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val departmentService = DepartmentServiceImpl(departmentRepository, mapper)
        val departmentDO1 = constructDepartmentDO(1)
        val departmentDO2 = constructDepartmentDO(2)
        val departmentBO1 = constructDepartmentBO(1)
        val departmentBO2 = constructDepartmentBO(2)

        Mockito.`when`(departmentRepository.allDepartments).thenReturn(mutableListOf(departmentDO1, departmentDO2))
        Mockito.`when`(mapper.map(departmentDO1, DepartmentBO::class.java)).thenReturn(departmentBO1)
        Mockito.`when`(mapper.map(departmentDO2, DepartmentBO::class.java)).thenReturn(departmentBO2)

        val allDepartments = departmentService.allDepartments
        assertEquals(2, allDepartments.size)
        assertEquals(departmentBO1.id, allDepartments[0].id)
        assertEquals(departmentBO1.name, allDepartments[0].name)
        assertEquals(departmentBO1.description, allDepartments[0].description)
        assertEquals(departmentBO2.id, allDepartments[1].id)
        assertEquals(departmentBO2.name, allDepartments[1].name)
        assertEquals(departmentBO2.description, allDepartments[1].description)

    }

    @Test
    fun testCreateDepartment() {
        val departmentRepository = Mockito.mock(DepartmentRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val departmentService = DepartmentServiceImpl(departmentRepository, mapper)

        val departmentDO1 = constructDepartmentDO(1)
        val departmentBO1 = constructDepartmentBO(1)

        Mockito.`when`(departmentRepository.createDepartment(departmentDO1)).thenReturn(departmentDO1)
        Mockito.`when`(mapper.map(departmentBO1, DepartmentDO::class.java)).thenReturn(departmentDO1)
        Mockito.`when`(mapper.map(departmentDO1, DepartmentBO::class.java)).thenReturn(departmentBO1)

        val created = departmentService.createDepartment(departmentBO1)
        assertNotNull(created)
        assertEquals(departmentBO1.name, created.name)
        assertEquals(departmentBO1.description, created.description)
        assertTrue { created.id > 0 }
    }

    @Test
    fun testUpdateExistingDepartment() {
        val departmentRepository = Mockito.mock(DepartmentRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val departmentService = DepartmentServiceImpl(departmentRepository, mapper)

        val departmentDO1 = constructDepartmentDO(1)
        val departmentBO1 = constructDepartmentBO(1)

        Mockito.`when`(mapper.map(departmentBO1, DepartmentDO::class.java)).thenReturn(departmentDO1)
        Mockito.`when`(mapper.map(departmentDO1, DepartmentBO::class.java)).thenReturn(departmentBO1)
        Mockito.`when`(departmentRepository.updateDepartment(departmentDO1)).thenReturn(departmentDO1)
        Mockito.`when`(departmentRepository.exists(1)).thenReturn(true)

        val updated = departmentService.updateDepartment(departmentBO1)
        assertNotNull(updated)
        assertEquals(departmentBO1.id, updated.id)
        assertEquals(departmentBO1.name, updated.name)
        assertEquals(departmentBO1.description, updated.description)
    }

    @Test
    fun testUpdateNonExistingDepartment() {
        val departmentRepository = Mockito.mock(DepartmentRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val departmentService = DepartmentServiceImpl(departmentRepository, mapper)

        val departmentDO1 = constructDepartmentDO(1)
        val departmentBO1 = constructDepartmentBO(1)

        Mockito.`when`(departmentRepository.exists(1)).thenReturn(false)

        assertThrows<DepartmentNotFoundException> { departmentService.updateDepartment(departmentBO1) }
    }

    @Test
    fun testDeleteDepartment() {
        val departmentRepository = Mockito.mock(DepartmentRepository::class.java)
        val mapper = Mockito.mock(Mapper::class.java)
        val departmentService = DepartmentServiceImpl(departmentRepository, mapper)

        Mockito.`when`(departmentRepository.deleteDepartmentById(1)).thenAnswer {  }
        assertDoesNotThrow { departmentService.deleteDepartmentById(1) }
    }

    private fun constructDepartmentBO(index: Int): DepartmentBO {
        val departmentBO = DepartmentBO()
        departmentBO.id = index
        departmentBO.name = "department$index"
        departmentBO.description = "department$index description"
        return departmentBO
    }

    private fun constructDepartmentDO(index: Int): DepartmentDO {
        val departmentDO = DepartmentDO()
        departmentDO.id = index;
        departmentDO.name = "person$index"
        departmentDO.description = "department$index description"
        return departmentDO
    }

}