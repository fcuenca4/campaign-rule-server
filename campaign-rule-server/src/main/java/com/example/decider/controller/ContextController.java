package com.example.decider.controller;

import com.example.decider.model.Context;
import com.example.decider.model.CurrencyAndAmount;
import com.example.decider.model.Payment;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.service.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ContextController {
    @Autowired
    private ContextService contextService;

    @PostMapping("/context")
    public ResponseEntity<?> postContext(@Valid @RequestBody Context context) {
        contextService.findAndPublish(context);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/context")
    public ResponseEntity<?> cntx() {

        List<CampaignRule> rules = new ArrayList<>();
        CurrencyAndAmount cc = new CurrencyAndAmount("ARS",new BigDecimal("1000"));
        Payment p1 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"qr",cc);
        Context cont1 = new Context(new BigInteger("100"),p1);
        contextService.findAndPublish(cont1);
        return ResponseEntity.ok().build();
    }
}
