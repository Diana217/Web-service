package org.example.model;

import java.util.regex.Pattern;

public class TypeEmail extends Type {
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    @Override
    public boolean validate(String value) {
        if (value == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(value).matches();
    }
}
