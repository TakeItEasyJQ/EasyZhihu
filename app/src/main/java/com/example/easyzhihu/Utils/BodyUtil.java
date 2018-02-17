package com.example.easyzhihu.Utils;

import android.graphics.Color;
import android.view.Display;
import android.webkit.WebView;

import com.example.easyzhihu.Activities.ActivityContent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/**
 * Created by My Computer on 2017/12/6.
 */

public class BodyUtil {
    //最新消息
    private Document document;
    private String htmltext;
    public BodyUtil(String htmltext){
        document=handleHTMLbody(htmltext);
        this.htmltext=htmltext;
    }

//得到Doc对象
    public Document handleHTMLbody(String htmltext){
        return Jsoup.parse(htmltext);
    }

    public void init(){
        handleContent(document);
        handleQuestionTitleSize(document);
        handleAuthorPicSize(document);
        handleAuthorNameColor(document);
        handleAuthorBio(document);
        handleHeadLine(document);
        handleViewMore(document);
    }
//得到更多的链接
    public String getViewMoreUrl(){
        return document.select("div.view-more").select("a").attr("href");
    }

    public void RemoveViewMore(){
        document.select("div.view-more").remove();
    }


    public String getNewContent(){
        return document.toString();
    }

//更改标题字号
   private void handleQuestionTitleSize(Document doc){
       doc.select("h2.question-title").get(0).attr("style","font-size:1.15em");
   }
    //更改作者头像大小，竖直居中
    private void handleAuthorPicSize(Document doc){
        for (Element e:doc.select("img.avatar")){
            e.attr("width","20").attr("height","20").attr("style","vertical-align:middle");
        }
    }

    //更改作者名称字体颜色,加粗，竖直居中
    private void handleAuthorNameColor(Document doc){
        for (Element e:doc.select("span.author")){
            e.attr("style","color:#313131;font-weight:bold;vertical-align:middle;padding-left:0.5em");
        }
    }

    //更改作者心情，颜色，竖直居中
    private void handleAuthorBio(Document doc){
        doc.select("span.bio").attr("style","color:#A9A9A9;vertical-align:middle;");
    }

    //更改正文字体颜色，链接颜色，图片适配屏幕
    private void  handleContent(Document doc){
        Elements elements=doc.select("div.content");
        elements.attr("style","color:#313131;font-size:1.1em;line-height:28px");
        for (Element e:elements){
            e.select("a").attr("style","color:#4682B4;text-decoration: none");
            e.select("img").attr("style","max-width:100%").attr("height","auto");
//            e.select("img.content-image").attr("style","max-width:100%").attr("height","auto");

        }
    }
//    text-decoration: none  消除连接下划线

    private void handleHeadLine(Document doc){
        doc.select("div.headline-background").attr("style","height:100px");
        Elements elements=doc.select("a.headline-background-link");
        elements.attr("style","color:#000000;font-size:1.10em;text-decoration: none");
        for (Element element:elements){
            element.select("div.heading").attr("style","color:#A9A9A9;font-size:0.85em;height:30px");
        }
    }

    private void handleViewMore(Document doc){

        Elements elementsout=doc.select("div.view-more");
        elementsout.attr("style","background-color:#f1f1f1;width:max-width=100%;height:30px;font-size:1.1em;text-align:center;line-height:30px");

        Elements elements=doc.select("div.view-more").select("a");
        elements.attr("style","color:#a9a9a9;text-decoration: none;");
    }

}
