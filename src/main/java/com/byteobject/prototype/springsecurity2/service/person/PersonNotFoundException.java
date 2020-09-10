package com.byteobject.prototype.springsecurity2.service.person;

import com.byteobject.prototype.springsecurity2.service.BaseException;

import java.util.List;

public class PersonNotFoundException extends BaseException {

    public static final String PERSON_BY_ID_NOT_FOUND = "person_by_id_not_found";

    public PersonNotFoundException(String errorCode, List<Object> args) {
        super(errorCode, args);
    }
}
