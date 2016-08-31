package com.jfilipovic.countrylist.rest;

import android.content.Context;
import android.util.Log;
import com.jfilipovic.countrylist.models.CountriesListHolder;
import com.jfilipovic.countrylist.models.Country;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;

public class RestService {

    private Context context;

    public RestService(Context context) {
        this.context = context;

    }

    public CountriesListHolder getCountriesList(){
        try {
            final String url = "https://restcountries.eu/rest/v1/all";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Country[] countries = restTemplate.getForObject(url, Country[].class);
            CountriesListHolder countriesListHolder = new CountriesListHolder();
            countriesListHolder.countriesList = Arrays.asList(countries);
            return countriesListHolder;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }

    public Country getSelectedCountry(String countryName){
        try {

            final String url = "https://restcountries.eu/rest/v1/name/" + countryName;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Log.d("REST", url);
            Country[] country = restTemplate.getForObject(url, Country[].class);
            return country[0];
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }


}
