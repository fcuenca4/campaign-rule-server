package com.decider.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotNull UUID id;

    private @NotNull UUID payeeAccountUuid;
    private @NotNull  UUID payeeUuid;
    private String reference;
    private @NotNull String currency;
    private @NotNull BigDecimal minorUnits;

    public Payment(@NotNull UUID payeeAccountUuid, @NotNull UUID payeeUuid, String reference, @NotNull String currency, @NotNull BigDecimal minorUnits) {
        this.payeeAccountUuid = payeeAccountUuid;
        this.payeeUuid = payeeUuid;
        this.reference = reference;
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    public Payment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPayeeAccountUuid() {
        return payeeAccountUuid;
    }

    public void setPayeeAccountUuid(UUID payeeAccountUuid) {
        this.payeeAccountUuid = payeeAccountUuid;
    }

    public UUID getPayeeUuid() {
        return payeeUuid;
    }

    public void setPayeeUuid(UUID payeeUuid) {
        this.payeeUuid = payeeUuid;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getMinorUnits() {
        return minorUnits;
    }

    public void setMinorUnits(BigDecimal minorUnits) {
        this.minorUnits = minorUnits;
    }
}
