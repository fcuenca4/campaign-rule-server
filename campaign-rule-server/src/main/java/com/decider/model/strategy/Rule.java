package com.decider.model.strategy;

import com.decider.model.Context;

public interface Rule {
      boolean when(Context context);
}
