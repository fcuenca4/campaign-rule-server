package com.decider.model.strategy.concrete;

import com.decider.model.Context;
import com.decider.model.strategy.CampaignRule;

import javax.persistence.Entity;
import java.math.BigDecimal;
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


    public QRCampaignModel() {

    }
}
