package com.example.onlinelibrary.gbapi.gbook;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RetailPrice {
    private Double amount = 0.0;
    private String currencyCode = "";

    public void setAmount(Double amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }

    public void setCurrencyCode(String currencyCode) {
        if (currencyCode != null) {
            this.currencyCode = currencyCode;
        }
    }
}
