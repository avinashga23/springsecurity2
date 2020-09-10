package com.byteobject.prototype.springsecurity2.service.department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    boolean exists(int id);

    Optional<DepartmentBO> getDepartmentById(int id);

    List<DepartmentBO> getAllDepartments();

    DepartmentBO createDepartment(DepartmentBO departmentBO);

    DepartmentBO updateDepartment(DepartmentBO departmentBO);

    void deleteDepartmentById(int id);

}
