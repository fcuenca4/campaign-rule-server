package com.example.decider.controller;

import com.example.decider.exception.InternalServerErrorException;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.service.CampaignService;
import com.example.decider.service.IdempotencyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class CampaignRuleController {

    private static final Logger logger = LogManager.getLogger(ContextController.class);
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private IdempotencyService<Integer,ResponseEntity> idempotencyService;

    @GetMapping("/campaign/rule/{campaignRuleId}")
    public ResponseEntity<?> getCampaignRule(@Valid @PathVariable UUID campaignRuleId) {
        int hashedKey = campaignRuleId.hashCode();
        ResponseEntity response = idempotencyService.getValue(hashedKey);
        if (response != null){
            return response;
        }
        response = ResponseEntity.ok(campaignService.findCampaignRuleById(campaignRuleId));
        idempotencyService.setValue(hashedKey,response);
        return response;
    }

    @PutMapping("/campaign/rule/{campaignRuleId}")
    public ResponseEntity<CampaignRule> updateCampaignRule(@PathVariable UUID campaignRuleId, @Valid @RequestBody CampaignRule campaignRule) {
        CampaignRule returnRule;
        try {
            returnRule = campaignService.update(campaignRuleId,campaignRule);
        }catch (DataAccessException err){
            throw new InternalServerErrorException("Internal Server Error");
        }
        return ResponseEntity.ok(returnRule);
    }

    @DeleteMapping("/campaign/rule/{campaignRuleId}")
    public ResponseEntity<CampaignRule> deleteAnswer(@PathVariable UUID campaignRuleId) {
        try {
            campaignService.delete(campaignRuleId);
        }catch (DataAccessException err){
            throw new InternalServerErrorException("Internal Server Error");
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/campaign/rule")
    public ResponseEntity<?> addCampaignRule(@Valid @RequestBody CampaignRule campaignRule) {
        int hashedKey = campaignRule.hashCode();
        ResponseEntity response = idempotencyService.getValue(hashedKey);
        if (response != null){
            return response;
        }
        try {
            response =  ResponseEntity.ok(campaignService.save(campaignRule));
        }catch (DataAccessException err){
            logger.error(err.toString());
            throw new InternalServerErrorException("Internal Server Error");
        }
        idempotencyService.setValue(hashedKey,response);
        return response;
    }
}