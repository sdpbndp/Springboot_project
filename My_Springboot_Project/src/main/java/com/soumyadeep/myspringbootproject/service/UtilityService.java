package com.soumyadeep.myspringbootproject.service;

import org.springframework.http.ResponseEntity;

public interface UtilityService {

    public ResponseEntity<Object> sendSuccessResponse(String message, Object data, Integer status);
    public ResponseEntity<Object> sendSuccessResponse(String message, Object data);
    public ResponseEntity<Object> sendSuccessResponse(Object data);

    public ResponseEntity<Object> sendErrorResponse(String message, Object data, Integer status);
    public ResponseEntity<Object> sendErrorResponse(String message, Object data);
    public ResponseEntity<Object> sendErrorResponse(Object data);

}
