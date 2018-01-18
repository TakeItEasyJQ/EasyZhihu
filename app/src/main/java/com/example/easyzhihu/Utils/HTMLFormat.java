package com.example.easyzhihu.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

/**
 * Created by My Computer on 2017/12/6.
 */

public class HTMLFormat {                                  //对body进行WebView适配的处理


        public static String getNewBody(String htmltext){

            Document doc= Jsoup.parse(htmltext);
            doc.select("div.content-inner").select("div.view-more").remove();

            Elements elements=doc.select("img.content-image");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            String s=doc.select("h2.question-title").get(0).text();
            Element element=doc.select("h2.question-title").get(0);
            element.attr("style","font-size:1.15em");

            Elements elements1=doc.select("div.content").select("a");
            for (Element e:elements1)
            e.attr("style","color:#4682B4");

            Elements elements2=doc.select("div.content-inner").select("img.avatar");
            for (Element e:elements2){
                e.attr("width","20").attr("height","20").attr("style","vertical-align:middle");
            }

            Elements elements3=doc.select("span.author");
            for (Element e:elements3){
                e.attr("style","color:#313131;font-weight:bold;vertical-align:middle;padding-left:0.5em");
            }
            return doc.toString();
        }

}
