package com.example.payments;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        Map<String, PaymentGateway> gateways = new HashMap<>();
        gateways.put("fastpay", new FastPayAdapter(new FastPayClient()));
        gateways.put("safecash", new SafeCashAdapter(new SafeCashClient()));

        String id1 = chargeWithProvider(gateways, "fastpay", "cust-1", 1299);
        String id2 = chargeWithProvider(gateways, "safecash", "cust-2", 1299);
        System.out.println(id1);
        System.out.println(id2);
    }

    private static String chargeWithProvider(
            Map<String, PaymentGateway> gateways,
            String provider,
            String customerId,
            int amountCents) {
        Objects.requireNonNull(provider, "provider");
        PaymentGateway gateway = gateways.get(provider);
        if (gateway == null) {
            throw new IllegalArgumentException("unknown provider: " + provider);
        }
        return new OrderService(gateway).charge(customerId, amountCents);
    }
}
