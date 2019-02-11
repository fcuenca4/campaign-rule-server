package com.example.decider.model;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "campaign_rules")
@Getter
@Setter
@AllArgsConstructor
public class CampaignRule{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID campaignId;
    private Date startDate;
    private Date endDate;
    private BigInteger discountAmount;
    private BigInteger percentage;
    private int priority;

    public CampaignRule(UUID campaignId, Date startDate, Date endDate, BigInteger discountAmount, BigInteger percentage, int priority) {
        this.campaignId = campaignId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountAmount = discountAmount;
        this.percentage = percentage;
        this.priority = priority;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(UUID campaignId) {
        this.campaignId = campaignId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigInteger getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigInteger discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigInteger getPercentage() {
        return percentage;
    }

    public void setPercentage(BigInteger percentage) {
        this.percentage = percentage;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
