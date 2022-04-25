package com.ddquin.simplefourms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="No access ")
public class NoAccessException extends RuntimeException {
}
