package com.back.ticketflow.service;

import com.back.ticketflow.model.Event;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey= secretKey;
    }

    public String createCheckoutSession(Event event, int quantity, boolean vip, int bookingId) throws StripeException {
        long amount = Math.round((vip ? event.getVipPrice() : event.getNormalPrice()) * 100);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/payment-success")
                .setCancelUrl("http://localhost:4200/payment-cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity((long) quantity)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur")
                                                .setUnitAmount(amount)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(event.getName() + " - " + event.getDate())
                                                                .build())
                                                .build())
                                .build())
                .putMetadata("bookingId", String.valueOf(bookingId))
                .build();

        Session session = Session.create(params);
        return session.getUrl();

    }
}
