package com.cookandroid.week10_11;

import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

public class ViewToXmlConverter {
    public static String convertViewToXml(View view) {
        try {
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            viewToXml(serializer, view);
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void viewToXml(XmlSerializer serializer, View view) throws Exception {
        // 뷰의 클래스 이름을 XML 태그로 사용하여 시작 태그를 생성합니다.
        serializer.startTag("", view.getClass().getSimpleName());

        // 뷰의 속성을 가져와서 XML로 변환합니다.
        serializer.attribute("", "id", String.valueOf(view.getId()));
        serializer.attribute("", "width", String.valueOf(view.getWidth()));
        serializer.attribute("", "height", String.valueOf(view.getHeight()));
        // 뷰의 추가 속성을 가져와서 XML로 변환합니다.
        // 필요한 속성을 여기에 추가합니다.

        // 뷰의 종료 태그를 생성합니다.
        serializer.endTag("", view.getClass().getSimpleName());
    }
}