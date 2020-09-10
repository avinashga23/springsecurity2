package com.byteobject.prototype.springsecurity2.repository.department

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

@Repository
class DepartmentRepositoryImpl : DepartmentRepository {

    companion object {
        private val departmentMap = HashMap<Int, DepartmentDO>()
        private val LOGGER = LoggerFactory.getLogger(DepartmentRepositoryImpl::class.java)
        private var id = 0
    }

    override fun exists(id: Int): Boolean {
        LOGGER.info("exists $id")
        return departmentMap.containsKey(id)
    }

    override fun getDepartmentById(id: Int): Optional<DepartmentDO> {
        LOGGER.info("get department by id $id")
        return Optional.ofNullable(departmentMap[id])
    }

    override fun getAllDepartments(): MutableList<DepartmentDO> {
        LOGGER.info("get all departments")
        return departmentMap.values.stream().collect(Collectors.toList())
    }

    override fun createDepartment(departmentDO: DepartmentDO?): DepartmentDO {
        LOGGER.info("create department")
        departmentDO!!
        departmentDO.id = ++id
        departmentMap[id] = departmentDO
        return departmentDO
    }

    override fun updateDepartment(departmentDO: DepartmentDO?): DepartmentDO {
        LOGGER.info("update department")
        departmentDO!!
        departmentMap[departmentDO.id] = departmentDO
        return departmentDO
    }

    override fun deleteDepartmentById(id: Int) {
        LOGGER.info("delete department by id $id")
        departmentMap.remove(id)
    }

}