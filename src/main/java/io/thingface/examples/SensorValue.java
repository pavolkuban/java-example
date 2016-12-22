package io.thingface.examples;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorValue {

    private double value;

    @JsonProperty("v")
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }    
    
}
