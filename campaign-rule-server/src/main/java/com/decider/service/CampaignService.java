package com.decider.service;

import com.decider.controller.ContextController;
import com.decider.exception.ResourceNotFoundException;
import com.decider.model.strategy.CampaignRule;
import com.decider.repository.CampaignRuleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class CampaignService {

    private static final Logger logger = LogManager.getLogger(ContextController.class);

    @Autowired
    private CampaignRuleRepository campaignRuleRepository;

    public Stream<CampaignRule> findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Date date) {
        return campaignRuleRepository.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(date);
    }

    public CampaignRule update(UUID campaignRuleId, CampaignRule campaignRule) {
        if(!campaignRuleRepository.existsById(campaignRuleId)) {
            throw new ResourceNotFoundException("Campaign Rule not found with id " + campaignRuleId);
        }
        CampaignRule persistedRule = findCampaignRuleById(campaignRuleId);
        persistedRule.setDiscountAmount(campaignRule.getDiscountAmount());
        persistedRule.setStartDate(campaignRule.getStartDate());
        persistedRule.setEndDate(campaignRule.getEndDate());
        persistedRule.setPercentage(campaignRule.getPercentage());
        persistedRule.setPriority(campaignRule.getPriority());
        persistedRule.setDiscountAmount(campaignRule.getDiscountAmount());
        try {
            campaignRuleRepository.save(persistedRule);
        }catch (DataAccessException err){
            logger.error("PERSISTANCE ERROR"+ err.toString());
            throw err;
        }
        return persistedRule;
    }
    public CampaignRule findCampaignRuleById(UUID campaignRuleId) {
        if (campaignRuleId != null && campaignRuleRepository.existsById(campaignRuleId)){
            return campaignRuleRepository.findById(campaignRuleId).get();
        }
        else{
            throw new ResourceNotFoundException("Campaign Rule not found with id " + campaignRuleId);
        }
    }

    public void delete(UUID campaignRuleId) {
        if(!campaignRuleRepository.existsById(campaignRuleId)) {
            throw new ResourceNotFoundException("Campaign Rule not found with id " + campaignRuleId);
        }
        try {
            campaignRuleRepository.deleteById(campaignRuleId);
        }catch (DataAccessException err){
            logger.error("PERSISTANCE ERROR"+ err.getStackTrace());
            throw err;
        }
    }

    public CampaignRule save(CampaignRule campaignRule) {
        CampaignRule returnRule;
        if(campaignRule.getId()!= null && campaignRuleRepository.existsById(campaignRule.getId())) {
            return campaignRuleRepository.findById(campaignRule.getId()).get();
        }
        try {
            returnRule = campaignRuleRepository.save(campaignRule);
        }catch (DataAccessException err){
            logger.error("PERSISTANCE ERROR"+ err.getStackTrace());
            throw err;
        }
        return returnRule;
    }
}
