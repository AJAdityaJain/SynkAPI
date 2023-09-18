package com.synk.controllers;

import com.synk.Application;
import com.synk.managers.SessionManager;
import com.synk.models.Message;
import com.synk.models.data.ErrorCode;
import com.synk.models.data.Pagination;
import com.synk.models.data.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/message", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
@ResponseStatus(HttpStatus.OK)
public class MessageController {
    @RequestMapping(method = RequestMethod.GET, value = "/load/{id}")
    public ResponseEntity<Request<Pagination<Message>>> loadMessages(String id,String uuid) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if(!SessionManager.CheckSession(id)){
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if (errors.size() > 0)
                return ResponseEntity.ok(new Request<>(errors.toArray(new ErrorCode[0])));
            else {
                return ResponseEntity.ok(
                        new Request<>(
                                new Pagination<Message>(
                                        3,
                                        Application.db.loadMessages(SessionManager.GetSession(id).uid, uuid)
                                ),
                                true
                        )
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}