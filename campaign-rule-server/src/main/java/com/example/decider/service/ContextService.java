package com.example.decider.service;


import com.example.decider.exception.InternalServerErrorException;
import com.example.decider.exception.PublishException;
import com.example.decider.model.Context;
import com.example.decider.model.CurrencyAndAmount;
import com.example.decider.model.Payment;
import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.model.strategy.concrete.QRCampaignModel;
import com.example.decider.repository.CampaignRuleRepository;
import com.example.decider.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    public List<CampaignRule> findValidRules(Context context) {
        createModel();
        List<CampaignRule> allRules = campaignService.findByStartDateBeforeAndEndDateAfterOrderByPriorityDesc(new Date())
                .collect(Collectors.toList());
        return rulesService.aplicableRules(allRules,context);
    }
    private Context createModel() {

        Date now = new Date();
        LocalDateTime dateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusYears(1);
        LocalDateTime dateYearBefor = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).minusYears(1);

        Date dateTo = Date.from(dateTime.toInstant(ZoneOffset.ofTotalSeconds(1)));
        Date yesterday = Date.from(dateYearBefor.toInstant(ZoneOffset.ofTotalSeconds(1)));


        CampaignRule cr1 = new QRCampaignModel(now,dateTo, new BigDecimal("10"), new BigDecimal("10"),1);
        CampaignRule cr2 = new QRCampaignModel(now,dateTo, new BigDecimal("100"), new BigDecimal("15"),1);
        CampaignRule cr3 = new QRCampaignModel(now,dateTo, new BigDecimal("1000"), new BigDecimal("20"),1);
        CampaignRule cr4 = new QRCampaignModel(now,dateTo, new BigDecimal("10000"), new BigDecimal("25"),1);
        CampaignRule cr5 = new QRCampaignModel(now,dateTo, new BigDecimal("100000"), new BigDecimal("100"),1);

        campaignRuleRepository.save(cr1);
        campaignRuleRepository.save(cr2);
        campaignRuleRepository.save(cr3);
        campaignRuleRepository.save(cr4);
        campaignRuleRepository.save(cr5);

        CurrencyAndAmount cc = new CurrencyAndAmount("ARS",new BigDecimal("1000"));
        CurrencyAndAmount cc2 = new CurrencyAndAmount("ARS",new BigDecimal("9393"));

        Payment p1 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"qr",cc);
        Payment p2 = new Payment(UUID.randomUUID(), UUID.randomUUID(),"qr",cc2);

        Context cont1 = new Context(new BigInteger("100"),p1);
        Context cont2 = new Context(new BigInteger("10000"),p2);



        p1.setCurrencyAndAmount(cc);
        cont1.setPayment(p1);
        contextRepository.save(cont1);
        return cont1;

    }

    @Async
    @Transactional
    public void findAndPublish(Context context) {
        List<CampaignRule> validRules = this.findValidRules(context);
        try {
            notificationService.publish(context, validRules);
        }catch (PublishException p){
            throw new InternalServerErrorException("Internal Server Exception");
        }
    }
}
