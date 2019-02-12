package com.example.decider.model.strategy;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
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
    private BigInteger discountAmount;
    private BigInteger percentage;
    private Integer priority;

    public CampaignRule(Date startDate, Date endDate, BigInteger discountAmount, BigInteger percentage, int priority) {
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
