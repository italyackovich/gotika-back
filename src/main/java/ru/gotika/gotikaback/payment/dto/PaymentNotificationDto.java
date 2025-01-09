package ru.gotika.gotikaback.payment.dto;

import lombok.Data;

@Data
public class PaymentNotificationDto {
    private String event;
    private PaymentObject object;

    @Data
    public static class PaymentObject {
        private String id;
        private String status;
        private Amount amount;

        @Data
        public static class Amount {
            private Double value;
            private String currency;
        }
    }
}
