package org.example.model;

public class TypeInteger extends Type {
    @Override
    public boolean validate(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
