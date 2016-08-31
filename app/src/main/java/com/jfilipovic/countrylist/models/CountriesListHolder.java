package com.jfilipovic.countrylist.models;

import java.util.ArrayList;
import java.util.List;

public class CountriesListHolder {


    public List<Country> countriesList= new ArrayList<Country>();

    public CountriesListHolder() {
        super();
    }

    public List<Country> getCountriesList() {
        return countriesList;
    }
}
