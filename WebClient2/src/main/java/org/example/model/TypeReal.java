package org.example.model;

public class TypeReal extends Type {
    @Override
    public boolean validate(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
