package com.example.decider.model.strategy;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "campaign_type")
@Table(name = "campaign")
public abstract class CampaignRule implements Rule,Comparable<CampaignRule>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Date startDate;
    private Date endDate;
    private BigDecimal discountAmount;
    private BigDecimal percentage;
    private Integer priority;

    public CampaignRule(Date startDate, Date endDate, BigDecimal discountAmount, BigDecimal percentage, int priority) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountAmount = discountAmount;
        this.percentage = percentage;
        this.priority = priority;
    }

    public CampaignRule() {
    }

    @Override
    public int compareTo(CampaignRule campaign) {
        return (this.getPriority()).compareTo(campaign.getPriority());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
