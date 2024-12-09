package ru.isupden.city.util.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.isupden.city.model.Climate;

@Component
public class StringToClimateEnumConverter implements Converter<String, Climate> {

    @Override
    public Climate convert(String source) {
        try {
            return source.isEmpty() ? null : Climate.valueOf(source.trim().toUpperCase());
        } catch(Exception e) {
            return null;
        }
    }
}
