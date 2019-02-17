package com.decider.unit;

import com.decider.model.Context;
import com.decider.model.Payment;
import com.decider.model.strategy.CampaignRule;
import com.decider.model.strategy.concrete.QRCampaignModel;
import com.decider.service.CampaignService;
import com.decider.service.ContextService;
import com.decider.service.NotificationService;
import com.decider.service.RulesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
public class ContextServiceUnitTest {
    @TestConfiguration
    static class ContextServiceIntegrationTestConfiguration{
        @Bean
        public ContextService contextService(){
            return new ContextService();
        }
    }
    @Autowired
    private ContextService contextService;
    @MockBean
    private CampaignService campaignService;
    @MockBean
    private RulesService rulesService;
    @MockBean
    NotificationService notificationService;

    @Test
    public void whenfindByStartDateBeforeAndEndDateAfterOrderByPriorityDesc_thenRuleShouldBeFound(){
        //GIVEN
        Date now = new Date();
        LocalDateTime dateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusYears(1);
        LocalDateTime dateYearBefor = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusYears(1);
        Date yesterday = Date.from(dateYearBefor.toInstant(ZoneOffset.ofTotalSeconds(1)));
        CampaignRule cr1 = new QRCampaignModel(now,yesterday, new BigDecimal("10"), new BigDecimal("10"),1);
        CampaignRule cr2 = new QRCampaignModel(now,yesterday, new BigDecimal("100"), new BigDecimal("15"),1);
        List<CampaignRule> list = new LinkedList<>();
        list.add(cr1);
        list.add(cr2);
        Stream<CampaignRule> stream = list.stream();
        Mockito.doReturn(stream).
                when(campaignService).
                findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(ArgumentMatchers.any());
        Mockito.doReturn(list).
                when(rulesService).
                aplicableRules(ArgumentMatchers.any(),ArgumentMatchers.any(Context.class));
        Payment payment = new Payment(UUID.randomUUID(),UUID.randomUUID(),"qr","ARS",new BigDecimal(10000));
        Context context = new Context(payment);
        //WHEN
        List<CampaignRule> rules = contextService.findValidRules(context);
        //THEN
        assert rules.size() == 2;
    }
}
