package com.mthree.model;

public class State {
    private String fullStateName;
    private String stateAbbreviation;

    public State(String fullStateName, String stateAbbreviation) {
        this.fullStateName = fullStateName;
        this.stateAbbreviation = stateAbbreviation;
    }

    public String getFullStateName() {
        return fullStateName;
    }

    public void setFullStateName(String fullStateName) {
        this.fullStateName = fullStateName;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }
}
