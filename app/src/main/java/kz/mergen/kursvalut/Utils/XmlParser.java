package kz.mergen.kursvalut.Utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by arman on 12.10.17.
 */

public class XmlParser {

    private StringBuffer datas;
    private static XmlParser instance;
    private XmlPullParser parser;
    public static XmlParser getInstance(){
        if(instance==null){
            return new XmlParser();
        }
        return instance;
    }

    public XmlParser parse(String xml){
        try {
            parser = Xml.newPullParser();
            parser.setInput(new StringReader(xml));
        } catch (Exception e){
            Log.i("AAA", "xmlparsererror = "+e.getMessage());
        }
        return this;
    }
    public StringBuffer findTag(String tagName, String[] elements){
        datas = new StringBuffer();
        try {
            while (parser.next()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG &&
                        parser.getName().equals(tagName)) {

                    while(parser.next()!=XmlPullParser.END_DOCUMENT){
                        for (String element : elements) {
                            if (parser.getEventType() == XmlPullParser.START_TAG && parser.getName().equals(element)) {
                                parser.next();
                                datas.append(parser.getText());
                                datas.append("##");
                            }

                        }
                    }

                }
            }
        } catch (Exception e) {
            Log.i("AAA","findTagError = "+e.getMessage());
        }
        return datas;
    }

}
