package org.example.model;

public class TypeChar extends Type {
    @Override
    public boolean validate(String value) {
        return value != null && value.length() == 1;
    }
}
