package com.c4c.housing.rest.controller;

import com.c4c.housing.adapter.RestAdapterV1;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.service.UserService;
import com.c4c.housing.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController( )
@RequestMapping(UserController.BASE_URL)
public class UserController {
    private final RestAdapterV1 restAdapterV1;
    static final String BASE_URL = "/api/v1/user";
    @Autowired
    public UserController(final RestAdapterV1 restAdapterV1) {
        this.restAdapterV1 = restAdapterV1;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResource> add(@RequestBody @Validated UserResource userResource){
        UserResource resource = this.restAdapterV1.save(userResource);
        return ResponseEntity.created(URI.create(BASE_URL+"users/"+resource.getId()))
                .body(resource);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResource> update(@RequestBody UserResource userResource){
        UserResource resource = this.restAdapterV1.update(userResource);
        return ResponseEntity.ok(resource);
    }

}
