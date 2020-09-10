package com.byteobject.prototype.springsecurity2.service.department;

import com.byteobject.prototype.springsecurity2.service.BaseException;

import java.util.List;

public class DepartmentNotFoundException extends BaseException {

    public static final String DEPARTMENT_BY_ID_NOT_FOUND = "department_by_id_not_found";

    public DepartmentNotFoundException(String errorCode, List<Object> args) {
        super(errorCode, args);
    }
}
