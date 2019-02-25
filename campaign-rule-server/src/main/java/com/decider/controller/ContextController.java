package com.decider.controller;

import com.decider.exception.InternalServerErrorException;
import com.decider.exception.PublishException;
import com.decider.model.Context;
import com.decider.service.ContextService;
import com.decider.service.IdempotencyService;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@CommonsLog
@RestController
public class ContextController {
    private static final Log logger = org.apache.commons.logging.LogFactory.getLog(ContextController.class);
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
            contextService.save(context);
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
