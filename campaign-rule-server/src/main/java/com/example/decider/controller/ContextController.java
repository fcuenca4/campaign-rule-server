package com.example.decider.controller;

import com.example.decider.model.CampaignRule;
import com.example.decider.model.Context;
import com.example.decider.model.CurrencyAndAmount;
import com.example.decider.model.Payment;
import com.example.decider.service.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class ContextController {
    @Autowired
    private ContextService contextService;

    @PostMapping("/context")
    public ResponseEntity<?> postContext(@Valid @RequestBody Context context) {
        try {
            List<CampaignRule> rules = contextService.findAllRules(context);
            return new ResponseEntity<>(rules, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/context")
    public ResponseEntity<?> postContext() {
        try {
            CurrencyAndAmount cc = new CurrencyAndAmount("ARS",new BigInteger("1000"));

            Payment p1 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"",cc);

            Context cont1 = new Context(new BigInteger("100"),p1);
            List<CampaignRule> rules = contextService.findAllRules(cont1);
            return new ResponseEntity<>(rules, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
