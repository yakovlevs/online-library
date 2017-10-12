package com.example.onlinelibrary.payment;

import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Log4j
@Component
public class Robokassa {
    @Value("${application.robokassa.merchantlogin}")
    private String merchantLogin;
    @Value("${application.robokassa.merchantpass1}")
    private String merchantPass1;
    @Value("${application.robokassa.merchantpass2}")
    private String merchantPass2;

    public boolean verifySignature(Double price, String invId, String user, String id, String sigFromPaymentSystem) {
        String result = price + ":" + invId + ":" + merchantPass2 + ":Shp_book=" + id + ":Shp_user=" + user;
        String genSig = stringToMd5(result);
        log.info("Signature from shop: " + sigFromPaymentSystem + " - " + genSig + " (generated)");
        return sigFromPaymentSystem.equals(genSig);
    }

    public String generateSignature(Double price, String user, String id) {
        String result = merchantLogin + ":" + price + "::" + merchantPass1 + ":Shp_book=" + id + ":Shp_user=" + user;
        return stringToMd5(result);
    }

    private String stringToMd5(@NonNull String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMerchantLogin() {
        return merchantLogin;
    }
}
