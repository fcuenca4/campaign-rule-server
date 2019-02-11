package com.example.decider.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
    private Context context;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "payment")
    private CurrencyAndAmount currencyAndAmount;
    @NotNull
    private UUID payeeAccountUid;
    @NotNull
    private UUID payeeUuid;
    private String reference;

    public Payment(@NotNull UUID payeeAccountUid, @NotNull UUID payeeUuid, String reference, @NotNull CurrencyAndAmount currencyAndAmount) {
        this.payeeAccountUid = payeeAccountUid;
        this.payeeUuid = payeeUuid;
        this.reference = reference;
        this.currencyAndAmount = currencyAndAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPayeeAccountUid() {
        return payeeAccountUid;
    }

    public void setPayeeAccountUid(UUID payeeAccountUid) {
        this.payeeAccountUid = payeeAccountUid;
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

    public CurrencyAndAmount getCurrencyAndAmount() {
        return currencyAndAmount;
    }

    public void setCurrencyAndAmount(CurrencyAndAmount currencyAndAmount) {
        this.currencyAndAmount = currencyAndAmount;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
