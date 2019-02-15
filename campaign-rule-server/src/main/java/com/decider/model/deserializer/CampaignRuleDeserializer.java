package com.decider.model.deserializer;

import com.decider.model.strategy.CampaignRule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CampaignRuleDeserializer extends StdDeserializer<CampaignRule> {

    protected CampaignRuleDeserializer(Class<?> vc) {
        super(vc);
    }

    protected CampaignRuleDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected CampaignRuleDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public CampaignRule deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}
