package com.synk.controllers;

import com.synk.Application;
import com.synk.managers.SessionManager;
import com.synk.models.Contact;
import com.synk.models.data.Empty;
import com.synk.models.UUID;
import com.synk.models.data.ErrorCode;
import com.synk.models.data.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
@ResponseStatus(HttpStatus.OK)
public class ContactController {
    @RequestMapping(method = RequestMethod.POST, value = "/post/{id}")
    public ResponseEntity<Response<Empty>> post(@PathVariable String id, String contact, String name) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();

            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }

            if (name == null || contact == null)
                errors.add(ErrorCode.ARGUMENT_NULL);


            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                Application.db.postContact(new Contact(new UUID(), SessionManager.GetSession(id), new UUID(contact), name));
                return ResponseEntity.ok(new Response<>(
                        Empty.New,
                        true
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get/{id}")
    public ResponseEntity<Response<Contact[]>> get(@PathVariable String id) {
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
                                Application.db.getContacts(SessionManager.GetSession(id).uid),
                                true
                        )
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update/{id}")
    public ResponseEntity<Response<Empty>> update(@PathVariable String id, String uuid, String name) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if(name == null)
                errors.add(ErrorCode.INVALID_NAME);
            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                Application.db.updateContact(SessionManager.GetSession(id).uid, uuid, name);
                return ResponseEntity.ok(
                        new Response<>(
                                Empty.New,
                                true
                        )
                );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<Response<Empty>> delete(@PathVariable String id, UUID uuid) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();
            if (!SessionManager.CheckSession(id)) {
                errors.add(ErrorCode.INVALID_SESSION);
            }
            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                Application.db.deleteContact(uuid.uid, SessionManager.GetSession(id).uid);
                return ResponseEntity.ok(new Response<>(
                        Empty.New,
                        true
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}