package com.example.decider.repository;


import com.example.decider.model.strategy.CampaignRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface  CampaignRuleRepository extends JpaRepository<CampaignRule, UUID> {
    Stream<CampaignRule> findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Date startDate, Date endDate);
    default Stream<CampaignRule> findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(Date givenDate) {
        return findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(givenDate, givenDate);
    }
}
