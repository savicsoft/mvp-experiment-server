package com.savicsoft.carpooling.user.exception;

import lombok.experimental.StandardException;
import org.springframework.dao.DataAccessException;

@StandardException
public class UserDataPersistanceException extends DataAccessException {

}
