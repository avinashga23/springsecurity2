package com.byteobject.prototype.springsecurity2.repository.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    boolean exists(int id);

    Optional<DepartmentDO> getDepartmentById(int id);

    List<DepartmentDO> getAllDepartments();

    DepartmentDO createDepartment(DepartmentDO departmentDO);

    DepartmentDO updateDepartment(DepartmentDO departmentDO);

    void deleteDepartmentById(int id);

}
