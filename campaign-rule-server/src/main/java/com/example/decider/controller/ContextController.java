package com.example.decider.controller;

import com.example.decider.exception.InternalServerErrorException;
import com.example.decider.exception.PublishException;
import com.example.decider.model.Context;
import com.example.decider.service.ContextService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ContextController {
    @Autowired
    private ContextService contextService;
    private static final Logger logger = LogManager.getLogger(ContextController.class);

    @PostMapping("/context")
    public ResponseEntity<?> postContext(@Valid @RequestBody Context context) {
        try {
            contextService.findAndPublish(context);
            return ResponseEntity.ok().build();
        }catch (PublishException p){
            logger.error(p.getStackTrace());
            throw new InternalServerErrorException("internal server error");
        }
    }
}
