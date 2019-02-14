package com.example.decider.model;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Objects;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Context)) return false;
        Context context = (Context) o;
        return Objects.equals(getId(), context.getId()) &&
                Objects.equals(getMaximumAmount(), context.getMaximumAmount()) &&
                Objects.equals(getPayment(), context.getPayment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMaximumAmount(), getPayment());
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
