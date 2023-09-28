package com.synk.controllers;

import com.google.common.hash.Hashing;
import com.synk.Application;
import com.synk.managers.SessionManager;
import com.synk.models.data.Empty;
import com.synk.models.UUID;
import com.synk.models.User;
import com.synk.models.data.ErrorCode;
import com.synk.models.data.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
@ResponseStatus(HttpStatus.OK)
public class UserController {
//    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/signUp")
    public ResponseEntity<Response<UUID>> signUp(@RequestBody User user) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();

            if (user.email == null || !Application.patternMatches(user.email, "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))
                errors.add(ErrorCode.INVALID_EMAIL);
            else
                user.email = user.email.toUpperCase();
            if (user.hash == null || user.hash.length() < 8)
                errors.add(ErrorCode.INVALID_PASSWORD);
            if(user.name == null || user.name.length() < 3)
                errors.add(ErrorCode.INVALID_NAME);
            if(user.privateKey == null || user.publicKey == null)
                errors.add(ErrorCode.ARGUMENT_NULL);
            if (Application.db.getUUID(user.email).uid != null)
                errors.add(ErrorCode.EXISTS);


            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                user.hash = Hashing.sha256().hashString("$Hashing#$" + user.email + user.hash, StandardCharsets.UTF_8).toString();
                return ResponseEntity.ok(new Response<>(
                        SessionManager.AddSession(Application.db.postUser(user)),
                        true
                ));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/autoSignIn/{id}")
    public ResponseEntity<Response<Empty>> autoSignIn(@PathVariable String id) {
        try {
            if (!SessionManager.CheckSession(id))
                return ResponseEntity.ok(new Response<>(ErrorCode.INVALID_SESSION));
            else
                return ResponseEntity.ok(new Response<>(Empty.New, true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/signIn")
    public ResponseEntity<Response<User>> signIn(String email, String password) {
        try {
            ArrayList<ErrorCode> errors = new ArrayList<>();

            if (password == null)
                errors.add(ErrorCode.INVALID_PASSWORD);
            if (email == null)
                errors.add(ErrorCode.INVALID_EMAIL);
            else
                email = email.toUpperCase();
            if (Application.db.getUUID(email) == null)
                errors.add(ErrorCode.NOT_EXISTS);


            if (errors.size() > 0)
                return ResponseEntity.ok(new Response<>(errors.toArray(new ErrorCode[0])));
            else {
                User user = Application.db.getUser(email);

                if (Hashing.sha256().hashString("$Hashing#$" + email + password, StandardCharsets.UTF_8).toString().equals(user.hash)) {
                    user.uuid = SessionManager.AddSession(user.uuid);
                    return ResponseEntity.ok(new Response<>(user, true));
                } else {
                    return ResponseEntity.ok(new Response<>(ErrorCode.UNAUTHORIZED));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}