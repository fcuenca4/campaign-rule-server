package com.decider.controller;

import com.decider.exception.PublishException;
import com.decider.model.Context;
import com.decider.exception.InternalServerErrorException;
import com.decider.service.ContextService;
import com.decider.service.IdempotencyService;
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
    private static final Logger logger = LogManager.getLogger(ContextController.class);
    @Autowired
    private ContextService contextService;
    @Autowired
    private IdempotencyService<Integer,ResponseEntity> idempotencyService;

    @PostMapping("/context")
    public ResponseEntity<?> postContext(@Valid @RequestBody Context context) {
        try {
            int hashKey = context.hashCode();
            ResponseEntity response = idempotencyService.getValue(hashKey);
            if (response != null){
                return response;
            }
            contextService.findAndPublish(context);
            response = ResponseEntity.ok().build();
            idempotencyService.setValue(hashKey,response);
            return response;
        }catch (PublishException p){
            logger.error(p.getStackTrace());
            throw new InternalServerErrorException("internal server error");
        }
    }
}
