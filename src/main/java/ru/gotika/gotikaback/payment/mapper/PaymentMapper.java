package ru.gotika.gotikaback.payment.mapper;

import org.mapstruct.*;
import ru.gotika.gotikaback.common.util.MapperUtil;
import ru.gotika.gotikaback.payment.dto.RequestPaymentDto;
import ru.gotika.gotikaback.payment.dto.ResponsePaymentDto;
import ru.gotika.gotikaback.payment.enums.PaymentStatus;
import ru.gotika.gotikaback.payment.model.Payment;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        uses = {MapperUtil.class},
        imports = {PaymentStatus.class, LocalDateTime.class})
public interface PaymentMapper {
    List<RequestPaymentDto> paymentListToPaymentDtoList(List<Payment> paymentList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "paymentStatus", expression = "java(PaymentStatus.NOT_PAID)")
    @Mapping(target = "paymentDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "order", source = "orderId", qualifiedByName = "idToOrder")
    Payment requestPaymentDtoToPayment(RequestPaymentDto paymentDto);

    @Mapping(target = "orderId", source = "order.id")
    ResponsePaymentDto paymentToResponsePaymentDto(Payment payment);
}
