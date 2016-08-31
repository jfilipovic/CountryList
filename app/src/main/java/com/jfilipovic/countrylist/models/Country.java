package com.jfilipovic.countrylist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    private String name;
    private String capital;
    private String subregion;
    private String population;
    private String alpha2Code;

    public String getName() {
        return this.name;
    }

    public String getCapital() {
        return this.capital;
    }

    public String getSubregion() {
        return this.subregion;
    }

    public String getPopulation() {
        return this.population;
    }

    public String getAlpha2Code(){
        return  this.alpha2Code;
    }
}
