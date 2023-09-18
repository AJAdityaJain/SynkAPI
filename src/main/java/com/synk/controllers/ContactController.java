package com.synk.controllers;

import com.synk.Application;
import com.synk.managers.SessionManager;
import com.synk.models.Contact;
import com.synk.models.UUID;
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
@RequestMapping(value = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
@ResponseStatus(HttpStatus.OK)
public class ContactController {
    @RequestMapping(method = RequestMethod.POST, value = "/post/{id}")
    public ResponseEntity<Request<Boolean>> post(String id, String contact, String name) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();

            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }

            if (name == null || contact == null)
                errors.add(ErrorCode.ARGUMENT_NULL);


            if (errors.size() > 0)
                return ResponseEntity.ok(new Request<>(errors.toArray(new ErrorCode[0])));
            else {
                Application.db.postContact(new Contact(UUID.New, SessionManager.GetSession(id), new UUID(contact), name));
                return ResponseEntity.ok(new Request<>(
                            true,
                            true
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    public ResponseEntity<Request<Pagination<Contact>>> get(String id) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if (errors.size() > 0)
                return ResponseEntity.ok(new Request<>(errors.toArray(new ErrorCode[0])));
            else {
                return ResponseEntity.ok(
                        new Request<>(
                                new Pagination<>(
                                        10,
                                        Application.db.getContacts(SessionManager.GetSession(id).uid)
                                ),
                                true
                        )
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<Request<Boolean>> delete(String id, UUID uuid) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if (errors.size() > 0)
                return ResponseEntity.ok(new Request<>(errors.toArray(new ErrorCode[0])));
            else {
                Application.db.deleteContact(uuid.uid, SessionManager.GetSession(id).uid);
                return ResponseEntity.ok(new Request<>(
                        true,
                        true
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}