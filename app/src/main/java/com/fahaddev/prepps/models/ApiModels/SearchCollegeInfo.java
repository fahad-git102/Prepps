package com.fahaddev.prepps.models.ApiModels;

public class SearchCollegeInfo {
    String state, name, program_offered;
    int minFees, maxFees, population;

    public SearchCollegeInfo(String state, String name, int minFees, int maxFees, int population, String program_offered) {
        this.state = state;
        this.name = name;
        this.minFees = minFees;
        this.maxFees = maxFees;
        this.population = population;
        this.program_offered = program_offered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinFees() {
        return minFees;
    }

    public void setMinFees(int minFees) {
        this.minFees = minFees;
    }

    public int getMaxFees() {
        return maxFees;
    }

    public void setMaxFees(int maxFees) {
        this.maxFees = maxFees;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getProgram_offered() {
        return program_offered;
    }

    public void setProgram_offered(String program_offered) {
        this.program_offered = program_offered;
    }
}
