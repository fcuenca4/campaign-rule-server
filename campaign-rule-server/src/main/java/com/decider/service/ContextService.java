package com.decider.service;


import com.decider.exception.PublishException;
import com.decider.model.Context;
import com.decider.model.strategy.CampaignRule;
import com.decider.repository.ContextRepository;
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
    RulesService rulesService;
    @Autowired
    CampaignService campaignService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    ContextRepository contextRepository;


    public List<CampaignRule> findValidRules(Context context) {
        List<CampaignRule> allRules = campaignService.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(new Date())
                .collect(Collectors.toList());
        return rulesService.aplicableRules(allRules,context);
    }

    @Async
    @Transactional
    public void findAndPublish(Context context) throws PublishException {
        List<CampaignRule> validRules = this.findValidRules(context);
        notificationService.publish(context, validRules);

    }

    public void save(Context context) {
        contextRepository.save(context);
    }
}
