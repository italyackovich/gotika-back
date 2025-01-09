package ru.gotika.gotikaback.payment.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.order.model.Order;
import ru.gotika.gotikaback.payment.dto.PaymentDto;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;
import ru.gotika.gotikaback.payment.model.Payment;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {PaymentStatus.class, LocalDateTime.class})
public interface PaymentMapper {
    List<PaymentDto> paymentListToPaymentDtoList(List<Payment> paymentList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "paymentStatus", defaultExpression = "java(PaymentStatus.NOT_PAID)")
    @Mapping(target = "paymentDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    Payment paymentDtoToPayment(PaymentDto paymentDto);

    @Mapping(target = "orderId", source = "order.id")
    PaymentDto paymentToPaymentDto(Payment payment);

    @Named("idToOrder")
    default Order idToOrder(Long orderId) {
        if (orderId == null) return null;
        Order order = new Order();
        order.setId(orderId);
        return order;
    }
}
