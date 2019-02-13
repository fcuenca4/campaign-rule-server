package com.example.decider.model.strategy;

import com.example.decider.model.Context;

public interface Rule {
      boolean when(Context context);
}
