package com.cookandroid.week10_11;

// XmlToViewConverter.java

import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.StringReader;

public class XmlToViewConverter {

    public static View convertXmlToView(String xml, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xml));
            // 인플레이션 시에 parent를 지정하여 레이아웃을 추가합니다.
            return inflater.inflate(parser, parent, true);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
}