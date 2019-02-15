package com.decider.model;


import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @MapsId
    @NotNull
    private Payment payment;

    public Context( @NotNull Payment payment) {
        this.payment = payment;
    }
    public Context() {
    }
    public UUID getId() {
        return id;
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
                Objects.equals(getPayment(), context.getPayment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPayment());
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
