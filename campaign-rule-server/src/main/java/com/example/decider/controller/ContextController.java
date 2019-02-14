package com.example.decider.controller;

import com.example.decider.exception.InternalServerErrorException;
import com.example.decider.exception.PublishException;
import com.example.decider.model.Context;
import com.example.decider.service.ContextService;
import com.example.decider.service.IdempotencyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ContextController {
    private static final Logger logger = LogManager.getLogger(ContextController.class);
    @Autowired
    private ContextService contextService;
    @Autowired
    private IdempotencyService<UUID,ResponseEntity> idempotencyService;

    @PostMapping("/context")
    public ResponseEntity<?> postContext(@Valid @RequestBody Context context) {
        try {
            ResponseEntity response = idempotencyService.getValue(context.getId());
            if (response != null){
                return response;
            }
            contextService.findAndPublish(context);
            response = ResponseEntity.ok().build();
            idempotencyService.setValue(context.getId(),response);
            return response;
        }catch (PublishException p){
            logger.error(p.getStackTrace());
            throw new InternalServerErrorException("internal server error");
        }
    }
}
