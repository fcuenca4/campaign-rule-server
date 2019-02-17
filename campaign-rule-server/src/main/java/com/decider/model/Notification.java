package com.decider.model;

import com.decider.model.strategy.CampaignRule;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import java.util.List;

@Scope("prototype")
@Component
public class Notification extends AuditModel {

    private Context context;

    @Transient
    private List<CampaignRule> campaignRules;
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<CampaignRule> getCampaignRules() {
        return campaignRules;
    }

    public void setCampaignRules(List<CampaignRule> campaignRules) {
        this.campaignRules = campaignRules;
    }
}
