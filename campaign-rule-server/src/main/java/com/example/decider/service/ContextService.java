package com.example.decider.service;

import com.example.decider.model.CampaignRule;
import com.example.decider.model.Context;
import com.example.decider.model.CurrencyAndAmount;
import com.example.decider.model.Payment;
import com.example.decider.repository.CampaignRuleRepository;
import com.example.decider.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContextService {
    @Autowired
    private CampaignRuleRepository campaignRuleRepository;

    @Autowired
    private ContextRepository contextRepository;
    @Transactional
    public List<CampaignRule> findAllRules(Context context) {
        createModel(); //ToDo
        List<CampaignRule> returnList = new ArrayList<>();
        List<CampaignRule> allRules = campaignRuleRepository.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(new Date())
                .collect(Collectors.toList());
        allRules.forEach(campaignRule -> {
            BigInteger amount = context.getPayment().getCurrencyAndAmount().getMinorUnits();
            BigInteger staticDiscount = campaignRule.getDiscountAmount().subtract(amount);
            BigInteger totalDiscount = staticDiscount.multiply(campaignRule.getPercentage()).divide(new BigInteger("100"));
            BigInteger totalAmount =  amount.subtract(totalDiscount);
            if (totalAmount.compareTo(BigInteger.ZERO) < 0) { // totalDiscount < 0
                returnList.add(campaignRule);
            }
        });
        return returnList;
    }

    private void createModel() {

        Date now = new Date();
        LocalDateTime dateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusYears(1);
        Date dateTo = Date.from(dateTime.toInstant(ZoneOffset.ofTotalSeconds(1)));


        CampaignRule cr1 = new CampaignRule(UUID.randomUUID(),now,dateTo, new BigInteger("10"), new BigInteger("10"),1);
        CampaignRule cr2 = new CampaignRule(UUID.randomUUID(),now,dateTo, new BigInteger("100"), new BigInteger("15"),1);
        CampaignRule cr3 = new CampaignRule(UUID.randomUUID(),now,dateTo, new BigInteger("1000"), new BigInteger("20"),1);
        CampaignRule cr4 = new CampaignRule(UUID.randomUUID(),now,dateTo, new BigInteger("10000"), new BigInteger("25"),1);
        CampaignRule cr5 = new CampaignRule(UUID.randomUUID(),now,dateTo, new BigInteger("100000"), new BigInteger("100"),1);

        campaignRuleRepository.save(cr1);
        campaignRuleRepository.save(cr2);
        campaignRuleRepository.save(cr3);
        campaignRuleRepository.save(cr4);
        campaignRuleRepository.save(cr5);

        CurrencyAndAmount cc = new CurrencyAndAmount("ARS",new BigInteger("1000"));
        CurrencyAndAmount cc2 = new CurrencyAndAmount("ARS",new BigInteger("9393"));

        Payment p1 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"",cc);
        Payment p2 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"",cc2);

        Context cont1 = new Context(new BigInteger("100"),p1);
        Context cont2 = new Context(new BigInteger("10000"),p2);


        p1.setCurrencyAndAmount(cc);
        cont1.setPayment(p1);
        contextRepository.save(cont1);


    }
}
