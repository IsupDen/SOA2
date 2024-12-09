package ru.isupden.city.util.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.isupden.city.model.Government;

@Component
public class StringToGovernmentEnumConverter implements Converter<String, Government> {

    @Override
    public Government convert(String source) {
        try {
            return source.isEmpty() ? null : Government.valueOf(source.trim().toUpperCase());
        } catch(Exception e) {
            return null;
        }
    }
}