package com.example.decider.service;

import com.example.decider.model.Context;
import com.example.decider.model.strategy.CampaignRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulesService {
    @Autowired
    RulesSelector rulesSelector;

    List<CampaignRule> aplicableRules(List<CampaignRule> allRules, Context context) {
        return rulesSelector.aplicableRules(allRules,context);
    }
}
