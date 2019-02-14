package com.example.decider.service;

import com.example.decider.model.Context;
import com.example.decider.model.strategy.CampaignRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RulesService {

    List<CampaignRule> aplicableRules(List<CampaignRule> allRules, Context context) {
        return this.rulesSelector(allRules,context);
    }

    private List<CampaignRule> rulesSelector(List<CampaignRule> campaignRules, Context context) {
        List<CampaignRule> returnList = new ArrayList<>(campaignRules.size());
        BigDecimal remanent = context.getPayment().getMinorUnits();
        for (CampaignRule campaignRule : campaignRules) {
            BigDecimal temp = paymentRemanent(remanent, campaignRule);
            if (campaignRule.when(context) && temp.compareTo(BigDecimal.ZERO) > 0) {
                returnList.add(campaignRule);
                remanent = temp;
            }
        }
        return returnList;


    }
    /*
     * This method responsibility is to calculate the amount remain  when applying a specific rule
     * */
    private BigDecimal paymentRemanent(BigDecimal paymentAmount, CampaignRule campaignRule) {
        BigDecimal paymentWithPercentageApplied = paymentAmount.multiply(BigDecimal.ONE.subtract(campaignRule.getPercentage()));
        return  paymentWithPercentageApplied.subtract(campaignRule.getDiscountAmount()); // paymentAmount - campaign direct discount

    }
}
