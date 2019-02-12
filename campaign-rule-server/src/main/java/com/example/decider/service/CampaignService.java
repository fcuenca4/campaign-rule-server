package com.example.decider.service;

import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.repository.CampaignRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Stream;

@Service
public class CampaignService {

    @Autowired
    CampaignRuleRepository campaignRuleRepository;

    Stream<CampaignRule> findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Date date) {
        return campaignRuleRepository.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(date);
    }
}
