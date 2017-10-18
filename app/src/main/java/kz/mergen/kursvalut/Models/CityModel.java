package kz.mergen.kursvalut.Models;

/**
 * Created by arman on 30.09.17.
 */

public class CityModel {
    private String country_header;
    private String[] city_child;

    public CityModel(String country_header, String[] city_child) {
        this.country_header = country_header;
        this.city_child = city_child;
    }

    public String getCountry_header() {
        return country_header;
    }

    public String[] getCity_child() {
        return city_child;
    }
}
