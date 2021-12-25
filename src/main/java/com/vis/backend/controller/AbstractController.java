package com.vis.backend.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public abstract class AbstractController<S> {
    @Autowired
    protected S service;

    protected <T>ResponseEntity<T> response(Optional<T> response) {
        if (response.isEmpty()) {
            return new ResponseEntity<T>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<T>(response.get(), HttpStatus.OK);
    }
}
