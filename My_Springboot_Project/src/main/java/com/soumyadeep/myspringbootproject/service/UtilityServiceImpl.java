package com.soumyadeep.myspringbootproject.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UtilityServiceImpl implements UtilityService{

    @Override
    public ResponseEntity<Object> sendSuccessResponse(String message, Object data, Integer status) {
        return ResponseEntity.status(status).body(getFinalData(1, data, message));
    }

    @Override
    public ResponseEntity<Object> sendSuccessResponse(String message, Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(getFinalData(1, data, message));
    }

    @Override
    public ResponseEntity<Object> sendSuccessResponse(Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(getFinalData(1, data, ""));
    }

    @Override
    public ResponseEntity<Object> sendErrorResponse(String message, Object data, Integer status) {
        return ResponseEntity.status(status).body(getFinalData(0, data, message));
    }

    @Override
    public ResponseEntity<Object> sendErrorResponse(String message, Object data) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getFinalData(0, data, message));
    }

    @Override
    public ResponseEntity<Object> sendErrorResponse(Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(getFinalData(0, data, ""));
    }

    private HashMap<String, Object> getFinalData(Integer success, Object data, String message){
        HashMap<String, Object> finalData = new HashMap<>();
        finalData.put("success", success);
        if(success == 1)
            finalData.put("data", data);
        if (success == 0)
            finalData.put("error", data);
        finalData.put("message", message);
        return finalData;
    }
}
