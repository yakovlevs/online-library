package com.example.onlinelibrary.gbapi.gbook;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaleInfo {
    private String saleability = "";
    private RetailPrice retailPrice = new RetailPrice();

    public void setSaleability(String saleability) {
        if (saleability != null) {
            this.saleability = saleability;
        }
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        if (retailPrice != null) {
            this.retailPrice = retailPrice;
        }
    }
}
