package com.example.decider.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency_amounts")
public class CurrencyAndAmount {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    //@MapsId
    @PrimaryKeyJoinColumn(name = "id",referencedColumnName = "id")
    private Payment payment;
    @NotNull
    private String currency;
    private @NotNull BigDecimal minorUnits;

    public CurrencyAndAmount(@NotNull String currency, @NotNull BigDecimal minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public @NotNull BigDecimal getMinorUnits() {
        return minorUnits;
    }

    public void setMinorUnits(@NotNull BigDecimal minorUnits) {
        this.minorUnits = minorUnits;
    }


}
