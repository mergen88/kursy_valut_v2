package kz.mergen.kursvalut.Models;

/**
 * Created by arman on 30.09.17.
 */

public class ResponceModel {
    int errorCode;
    String responce;


    public ResponceModel(int errorCode, String responce) {
        this.errorCode = errorCode;
        this.responce = responce;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getResponce() {
        return responce;
    }
}
