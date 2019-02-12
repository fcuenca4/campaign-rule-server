package com.example.decider.controller;

import com.example.decider.model.Context;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.service.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContextController {
    @Autowired
    private ContextService contextService;

    @PostMapping("/context")
    public ResponseEntity<?> postContext(@Valid @RequestBody Context context) {
        try {
            List<CampaignRule> rules = contextService.findValidRules(context);
            return new ResponseEntity<>(rules, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
