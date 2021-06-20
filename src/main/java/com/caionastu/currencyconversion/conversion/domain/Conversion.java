package com.caionastu.currencyconversion.conversion.domain;

import com.caionastu.currencyconversion.conversion.application.request.ConversionRequest;
import com.caionastu.currencyconversion.user.domain.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Conversion {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_currency", referencedColumnName = "initials")
    private Currency originCurrency;

    @Column(name = "origin_value")
    private BigDecimal originValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destiny_currency", referencedColumnName = "initials")
    private Currency destinyCurrency;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "date")
    private ZonedDateTime date;

    public BigDecimal getDestinyValue() {
        return originValue.multiply(taxRate);
    }

    public static Conversion from(@NonNull ConversionRequest request, @NonNull BigDecimal taxRate, @NonNull User user) {
        return new Conversion(
                null,
                user,
                new Currency(request.getOriginCurrency()),
                request.getOriginValue(),
                new Currency(request.getDestinyCurrency()),
                taxRate,
                ZonedDateTime.now());
    }

}
