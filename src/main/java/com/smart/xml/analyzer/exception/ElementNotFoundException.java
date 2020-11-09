package com.smart.xml.analyzer.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String elementDescription) {
        super(String.format("Cannot find element (%s)", elementDescription));
    }
}