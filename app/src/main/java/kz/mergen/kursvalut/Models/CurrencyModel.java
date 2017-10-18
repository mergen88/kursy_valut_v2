package kz.mergen.kursvalut.Models;

/**
 * Created by arman on 01.10.17.
 */

public class CurrencyModel {
    private String currency_name;
    private double currency_prodaja;
    private double currency_pokupka;

    public CurrencyModel(String currency_name, double currency_prodaja, double currency_pokupka) {
        this.currency_name = currency_name;
        this.currency_prodaja = currency_prodaja;
        this.currency_pokupka = currency_pokupka;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public double getCurrency_prodaja() {
        return currency_prodaja;
    }

    public double getCurrency_pokupka() {
        return currency_pokupka;
    }
}
