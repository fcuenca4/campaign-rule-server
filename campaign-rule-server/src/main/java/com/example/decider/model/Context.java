package com.example.decider.model;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "context")
@Setter
public class Context {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Nullable
    private BigInteger maximumAmount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @MapsId
    @NotNull
    private Payment payment;

    public Context(@Nullable BigInteger maximumAmount, @NotNull Payment payment) {
        this.maximumAmount = maximumAmount;
        this.payment = payment;
    }
    public Context() {
    }
    public UUID getId() {
        return id;
    }

    @Nullable
    public BigInteger getMaximumAmount() {
        return maximumAmount;
    }

    public Payment getPayment() {
        return payment;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setMaximumAmount(@Nullable BigInteger maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
