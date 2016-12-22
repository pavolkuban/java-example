package io.thingface.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class DeviceCommand {

    private String name;
    private ArrayList<String> arguments;              

    public String getName() {
        return name;
    }

    @JsonProperty("c")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("a")
    public ArrayList<String> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }        
}
