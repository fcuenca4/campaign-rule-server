package com.example.decider.model.strategy;

import com.example.decider.model.AuditModel;
import com.example.decider.model.strategy.concrete.BigSellerCampaignModel;
import com.example.decider.model.strategy.concrete.QRCampaignModel;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "campaign_type")
@Table(name = "campaign")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "campaignName")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BigSellerCampaignModel.class),
        @JsonSubTypes.Type(value = QRCampaignModel.class),
})
public abstract class CampaignRule extends AuditModel implements Rule,Comparable<CampaignRule> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampaignRule)) return false;
        CampaignRule that = (CampaignRule) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getDiscountAmount(), that.getDiscountAmount()) &&
                Objects.equals(getPercentage(), that.getPercentage()) &&
                Objects.equals(getPriority(), that.getPriority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDiscountAmount(), getPercentage(), getPriority());
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
