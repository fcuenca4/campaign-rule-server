package com.example.decider.service;


import com.example.decider.exception.PublishException;
import com.example.decider.model.Context;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.repository.CampaignRuleRepository;
import com.example.decider.repository.ContextRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    CampaignRuleRepository campaignRuleRepository;
    @Autowired
    NotificationService notificationService;

    private static Logger log = LogManager.getLogger(ContextService.class);

    public List<CampaignRule> findValidRules(Context context) {
        List<CampaignRule> allRules = campaignService.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(new Date())
                .collect(Collectors.toList());
        return rulesService.aplicableRules(allRules,context);
    }

    @Async
    @Transactional
    public void findAndPublish(Context context) throws PublishException{
        log.info("hola");
        List<CampaignRule> validRules = this.findValidRules(context);
        notificationService.publish(context, validRules);
    }
}
