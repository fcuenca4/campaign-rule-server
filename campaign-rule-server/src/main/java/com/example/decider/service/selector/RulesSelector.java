package com.example.decider.service.selector;

import com.example.decider.model.Context;
import com.example.decider.model.strategy.CampaignRule;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class RulesSelector {

    public List<CampaignRule> aplicableRules(List<CampaignRule> rules, Context context){
        List<CampaignRule> returnList = new LinkedList<>();
        rules.forEach((rule) -> {
            if (rule.when(context)){
                returnList.add(rule);
            }
        });
    return returnList;
    }
}
