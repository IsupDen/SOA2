package ru.isupden.city.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.isupden.city.model.StandardOfLiving;

@Component
public class StringToStandardOfLivingEnumConverter implements Converter<String, StandardOfLiving> {

    @Override
    public StandardOfLiving convert(String source) {
        try {
            return source.isEmpty() ? null : StandardOfLiving.valueOf(source.trim().toUpperCase());
        } catch(Exception e) {
            return null;
        }
    }
}
