package com.example.decider.service;


import com.example.decider.model.*;
import com.example.decider.model.strategy.concrete.QRCampaignModel;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.repository.CampaignRuleRepository;
import com.example.decider.repository.ContextRepository;
import com.example.decider.service.selector.RulesSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContextService {
    @Autowired
    private CampaignRuleRepository campaignRuleRepository;
    @Autowired
    private ContextRepository contextRepository;
    @Autowired
    RulesSelector rulesSelector;
    @Transactional
    public List<CampaignRule> findAllRules(Context context) {
        List<CampaignRule> returnList = new ArrayList<>();
        createModel();
        List<CampaignRule> allRules = campaignRuleRepository.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(new Date())
                .collect(Collectors.toList());
//        allRules.forEach(campaign -> {
////            BigInteger amount = context.getPayment().getCurrencyAndAmount().getMinorUnits();
////            BigInteger staticDiscount = campaign.getDiscountAmount().subtract(amount);
////            BigInteger totalDiscount = staticDiscount.multiply(campaign.getPercentage()).divide(new BigInteger("100"));
////            BigInteger totalAmount =  amount.subtract(totalDiscount);
////            if (totalAmount.compareTo(BigInteger.ZERO) < 0) { // totalDiscount < 0
////                returnList.add(campaign);
////            }
////        });
        returnList = rulesSelector.aplicableRules(allRules,context);
        return returnList;
    }

    private Context createModel() {

        Date now = new Date();
        LocalDateTime dateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusYears(1);
        LocalDateTime dateYearBefor = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).minusYears(1);

        Date dateTo = Date.from(dateTime.toInstant(ZoneOffset.ofTotalSeconds(1)));
        Date yesterday = Date.from(dateYearBefor.toInstant(ZoneOffset.ofTotalSeconds(1)));


        CampaignRule cr1 = new QRCampaignModel(now,yesterday, new BigInteger("10"), new BigInteger("10"),1);
        CampaignRule cr2 = new QRCampaignModel(yesterday,now, new BigInteger("100"), new BigInteger("15"),1);
        CampaignRule cr3 = new QRCampaignModel(yesterday,now, new BigInteger("1000"), new BigInteger("20"),1);
        CampaignRule cr4 = new QRCampaignModel(yesterday,now, new BigInteger("10000"), new BigInteger("25"),1);
        CampaignRule cr5 = new QRCampaignModel(now,dateTo, new BigInteger("100000"), new BigInteger("100"),1);

        campaignRuleRepository.save(cr1);
        campaignRuleRepository.save(cr2);
        campaignRuleRepository.save(cr3);
        campaignRuleRepository.save(cr4);
        campaignRuleRepository.save(cr5);

        CurrencyAndAmount cc = new CurrencyAndAmount("ARS",new BigInteger("1000"));
        CurrencyAndAmount cc2 = new CurrencyAndAmount("ARS",new BigInteger("9393"));

        Payment p1 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"qr",cc);
        Payment p2 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"qr",cc2);

        Context cont1 = new Context(new BigInteger("100"),p1);
        Context cont2 = new Context(new BigInteger("10000"),p2);


        p1.setCurrencyAndAmount(cc);
        cont1.setPayment(p1);
        contextRepository.save(cont1);
        return cont1;

    }
}
