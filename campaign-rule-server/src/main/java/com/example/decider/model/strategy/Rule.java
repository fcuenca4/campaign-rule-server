package com.example.decider.model.strategy;

import com.example.decider.model.Context;

import java.math.BigInteger;

public interface Rule {

      boolean when(Context context);
      BigInteger then(Context context);

}
