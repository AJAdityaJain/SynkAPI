package com.synk.controllers;

import com.synk.Application;
import com.synk.managers.SessionManager;
import com.synk.models.Message;
import com.synk.models.User;
import com.synk.models.data.ErrorCode;
import com.synk.models.data.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/message", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
@ResponseStatus(HttpStatus.OK)
public class MessageController {

    @RequestMapping(method = RequestMethod.GET, value = "/load/{id}")
    public ResponseEntity<Response<Message[]>> loadMessages(@PathVariable String id, String uuid) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                return ResponseEntity.ok(
                        new Response<>(
                                Application.db.loadMessages(SessionManager.GetSession(id).uid, uuid),
                                true
                        )
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getChats/{id}")
    public ResponseEntity<Response<User[]>> getChats(@PathVariable String id) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                return ResponseEntity.ok(
                        new Response<>(
                                Application.db.getUsers(SessionManager.GetSession(id).uid),
                                true
                        )
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}