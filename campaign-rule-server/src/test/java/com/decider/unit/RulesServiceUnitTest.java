package com.decider.unit;

import com.decider.model.Context;
import com.decider.model.Payment;
import com.decider.model.strategy.CampaignRule;
import com.decider.model.strategy.concrete.QRCampaignModel;
import com.decider.service.RulesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
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

@RunWith(SpringRunner.class)
public class RulesServiceUnitTest {
    @TestConfiguration
    static class RulesServiceTestConfiguration{
        @Bean
        public RulesService rulesService(){
            return new RulesService();
        }
    }

    @Autowired
    RulesService rulesService;
    @Test
    public void whenRulesSelector_returnList(){
       List<CampaignRule> list = createTestRules();
       Context context = createContext();
        assert rulesService.aplicableRules(list,context).size() == 2;
    }
    private List<CampaignRule> createTestRules(){
        Date now = new Date();
        LocalDateTime dateYearAfter = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusYears(1);
        Date yesterday = Date.from(dateYearAfter.toInstant(ZoneOffset.ofTotalSeconds(1)));
        CampaignRule cr1 = new QRCampaignModel(new Date(),yesterday, new BigDecimal("10"), new BigDecimal("0.10"),1);
        CampaignRule cr2 = new QRCampaignModel(new Date(),yesterday, new BigDecimal("100"), new BigDecimal("0.15"),1);
        CampaignRule cr3 = new QRCampaignModel(new Date(),yesterday, new BigDecimal("100"), new BigDecimal("0.999"),1);
        List<CampaignRule> list = new LinkedList<>();
        list.add(cr1);
        list.add(cr2);
        list.add(cr3);

        return list;
    }
    private Context createContext(){
        Payment payment = new Payment(UUID.randomUUID(),UUID.randomUUID(),"qr","ARS",new BigDecimal(10000));
        return new Context(payment);

    }

}
