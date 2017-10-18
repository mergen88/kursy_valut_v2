package kz.mergen.kursvalut.Utils;

/**
 * Created by arman on 30.09.17.
 */

public class Constants {

    public static String TAG_NAME_NBK = "item";
    public static String CURRENCY = "title";
    public static String UPDATE_DATE = "pubDate";
    public static String CHANGE = "change";
    public static String MONEY = "description";
    public static String APP_RECEIVER = "app_receiver";
    public static String SERVICE_COMMAND = "serv_command";
    public static String OPTIONAL = "optional";
    public static String RESULT_DATA = "data";
    public static int STATUS_RUNNING = 101;
    public static int STATUS_ERROR = 102;
    public static int STATUS_PUNKT_LOADED = 103;
    public static int STATUS_BANK_LOADED = 104;
    public static int STATUS_LOCATION_LOADED = 105;

    public static String[] CITYS = {"almaty","astana","karaganda","aktobe","ridder","shymkent","taraz"};
    public static String KAZ_NBK = "http://www.nationalbank.kz/rss/rates_all.xml";
    public static String OBMENIKI = "http://profstudio.kz/valuta/punkts?city=";


    public static String GOOGLE_API_KEY = "AIzaSyARBcWVPiOdX2yxSq6zGTxBBp2BUb0pz1E";

    public static String[] CUR_ARRAY = new String[]{"AUD","GBP","DKK","AED","USD","EUR","CAD","CNY","KWD","KGS","LVL","MDL","NOK","SAR","RUR","XDR","SGD","TRL","UZS","UAH","SEK","CHF","EEK","KRW","JPY","BYN","PLN","ZAR","TRY","HUF","CZK","TJS","HKD","BRL","MYR","AZN","INR","THB","AMD","GEL","IRR","MXN"};


    public static enum SERVICE_METHOD{
        NBK, KURSKZ, SAVEDB, GETDB, GETLOC
    }
}
