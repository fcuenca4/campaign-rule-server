package com.example.decider.service;


import com.example.decider.model.Context;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContextService {
    @Autowired
    private ContextRepository contextRepository;
    @Autowired
    RulesService rulesService;
    @Autowired
    CampaignService campaignService;
    @Transactional
    public List<CampaignRule> findValidRules(Context context) {
        List<CampaignRule> allRules = campaignService.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(new Date())
                .collect(Collectors.toList());
        return rulesService.aplicableRules(allRules,context);
    }
}
