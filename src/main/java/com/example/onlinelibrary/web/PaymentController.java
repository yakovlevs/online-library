package com.example.onlinelibrary.web;

import com.example.onlinelibrary.domain.PurchasedBook;
import com.example.onlinelibrary.domain.User;
import com.example.onlinelibrary.payment.Robokassa;
import com.example.onlinelibrary.persistence.UserDao;
import com.example.onlinelibrary.services.BookService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Log4j
@Controller
@Scope("session")
public class PaymentController {
    private final UserDao userDao;
    private final Robokassa robokassa;
    private final UserSession us;

    @Autowired
    public PaymentController(UserDao userDao, Robokassa robokassa, UserSession us) {
        this.userDao = userDao;
        this.robokassa = robokassa;
        this.us = us;
    }

    @PostMapping("/payment/result")
    @ResponseBody
    public String purchaseResult(
            @RequestParam(value = "OutSum", required = false) String price,
            @RequestParam(value = "InvId", required = false) String invId,
            @RequestParam(value = "Shp_book", required = false) String gbookId,
            @RequestParam(value = "Shp_user", required = false) String payer,
            @RequestParam(value = "SignatureValue", required = false) String signatureValue
    ) {
        boolean verification = robokassa.verifySignature(Double.parseDouble(price), invId, payer, gbookId, signatureValue);
        if (verification) {
            User user = userDao.findByUserName(payer).orElse(null);
            if (user != null) {
                Set<PurchasedBook> purchasedBooks = user.getPurchasedBooks();
                purchasedBooks.add(PurchasedBook.builder().googleId(gbookId).build());
                user.setPurchasedBooks(purchasedBooks);
                userDao.save(user);
                List<User> all = userDao.findAll();
                log.info(all);
                us.setUser(null);
            }
        }
        log.info("Payment result: " + price + " " + invId + " " + signatureValue + " " + payer + " " + gbookId);
        log.info("Signature verification: " + verification);
        return "OK" + invId;
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "/";
    }

    @GetMapping("/payment/fail")
    public String paymentFail() {
        return "error";
    }
}
