package com.example.decider.controller;

import com.example.decider.exception.InternalServerErrorException;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.service.CampaignService;
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

    @GetMapping("/campaign/rule/{campaignRuleId}")
    public ResponseEntity<CampaignRule> getCampaignRule(@Valid @PathVariable UUID campaignRuleId) {
        return ResponseEntity.ok(campaignService.findCampaignRuleById(campaignRuleId));
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
    public ResponseEntity<CampaignRule> addCampaignRule(@Valid @RequestBody CampaignRule campaignRule) {
        CampaignRule returnRule;
        try {
            returnRule = campaignService.save(campaignRule);
        }catch (DataAccessException err){
            logger.error(err.toString());
            throw new InternalServerErrorException("Internal Server Error");
        }
        return ResponseEntity.ok(returnRule);
    }
}