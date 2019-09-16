package com.edge.htmlparser;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HtmlParser extends AsyncTask<Void, Void, String> {

    private OnHtmlParseListener onHtmlParseListener;

    public void setOnHtmlParseListener(OnHtmlParseListener onHtmlParseListener) {
        this.onHtmlParseListener = onHtmlParseListener;
    }

    private final String pageUrl = "http://youngsang.hs.kr/69366/subMenu.do";

    @Override
    protected String doInBackground(Void... voids) {
        try {
            StringBuilder sb = new StringBuilder();
            Document doc = Jsoup.connect("http://youngsang.hs.kr/dggb/module/board/selectBoardListAjax.do")
                    .header("Origin", "http://youngsang.hs.kr")
                    .header("Referer", "http://youngsang.hs.kr/69366/subMenu.do")
                    .header("Cookie", "WMONID=ZjPJO-iPSHo; JSESSIONID=3tjPQkhhasE8rpXFQGVqaKl9zu6icYmeaNoE3M4aK0Q6y1BvysUeDs7CPXfYs526.hostingwas3_servlet_engine8")
                    .data("bbsId", "BBSMSTR_000000008726")
                    .data("bbsTyCode", "notice")
                    .data("customRecordCountPerPage", "10")
                    .data("pageIndex:", "1").post();
            Elements trList = doc.select("tbody>tr");
            for (Element td : trList) {
                String subject = td.getElementsByClass("samu").get(0).text().trim();

                Elements elements = td.getAllElements();
                Log.d("Data",elements.html());
                String number = elements.get(1).text();
                String writer = elements.get(5).text();
                String date = elements.get(6).text();
                String views = elements.get(7).text();
                sb.append("넘버 : ");
                sb.append(number);
                sb.append("\n");
                sb.append("제목 : ");
                sb.append(subject);
                sb.append("\n");
                sb.append("작성자 : ");
                sb.append(writer);
                sb.append("\n");
                sb.append("날짜 : ");
                sb.append(date);
                sb.append("\n");
                sb.append("조회수 : ");
                sb.append(views);
                sb.append("\n");
                sb.append("\n");
            }
            //  Log.d("Data",trList.html());
            return sb.toString();
        } catch (IOException e) {
            Log.e("Error", "Html Parse Error" + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (onHtmlParseListener != null) {
            onHtmlParseListener.onHtmlParse(s);
        }
    }
}
