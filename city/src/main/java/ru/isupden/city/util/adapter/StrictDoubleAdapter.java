package ru.isupden.city.util.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class StrictDoubleAdapter extends XmlAdapter<String, Double> {

    @Override
    public Double unmarshal(String value) {
        try {
            BigDecimal bigDecimal = new BigDecimal(value);
            double doubleValue = bigDecimal.doubleValue();
            if (!bigDecimal.equals(BigDecimal.valueOf(doubleValue))) {
                throw new IllegalArgumentException("Значение " + value + " нельзя точно представить как Double.");
            }
            return doubleValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа: " + value, e);
        }
    }

    @Override
    public String marshal(Double value) {
        return value == null ? null : value.toString();
    }
}

