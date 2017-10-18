package kz.mergen.kursvalut;

import android.util.Log;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        testAddress();



        assertEquals(4,2+2);

    }

    void testAddress(){
        String address = "пр. ДОСТЫК д 296/1 уг. ул.Чайкиной ВЫШЕ Аль-Фараби".replaceAll("[,.\\-!]"," ").replaceAll("\\(.*","").replaceAll("\\s+"," ");
        String g[] = address.split("\\s");

        System.out.println(address.matches("д"));
    }
}