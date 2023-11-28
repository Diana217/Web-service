package org.example.model;

public class TypeString extends Type {
    @Override
    public boolean validate(String value) {
        return true; // All strings are valid
    }
}
