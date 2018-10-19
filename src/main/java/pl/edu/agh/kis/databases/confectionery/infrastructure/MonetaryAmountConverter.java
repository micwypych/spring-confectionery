package pl.edu.agh.kis.databases.confectionery.infrastructure;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter(autoApply = true)
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        if (monetaryAmount == null) {
            return null;
        }
        if (!monetaryAmount.getCurrency().getCurrencyCode().equals("PLN")) {
            throw new IllegalStateException("Invalid currency code expected: PLN but was: "+monetaryAmount.getCurrency().getCurrencyCode());
        }
        return monetaryAmount.getNumber().numberValue(BigDecimal.class);
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return Money.of(value, "PLN");
    }
}
