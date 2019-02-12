package com.example.decider.model.strategy.concrete;

import com.example.decider.model.strategy.CampaignRule;
import com.example.decider.model.Context;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
@Entity

public class QRCampaignModel extends CampaignRule {

    public QRCampaignModel(Date startDate, Date endDate, BigDecimal discountAmount, BigDecimal percentage, int priority) {
        super(startDate, endDate, discountAmount, percentage, priority);
    }

    @Override
    public boolean when(Context context) {
        return context.getPayment().getReference().equals("qr") ;
    }

    @Override
    public BigInteger then(Context context) {
        return new BigInteger("33");
    }

    public QRCampaignModel() {

    }
}
