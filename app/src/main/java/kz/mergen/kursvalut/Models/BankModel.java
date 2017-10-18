package kz.mergen.kursvalut.Models;

/**
 * Created by arman on 30.09.17.
 */

public class BankModel {
    private String valuta;
    private String money;
    private String date_change;
    private String money_change;

    public BankModel(String valuta, String money, String date_change, String money_change) {
        this.valuta = valuta;
        this.money = money;
        this.date_change = date_change;
        this.money_change = money_change;
    }

    public String getValuta() {
        return valuta;
    }

    public String getMoney() {
        return money;
    }

    public String getDate_change() {
        return date_change;
    }

    public String getMoney_change() {
        return money_change;
    }
}
