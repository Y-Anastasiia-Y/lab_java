package com.yeris.scrapeweblab;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataScraper {

    private final String touchUrl= "https://touch.com.ua";

    public List<TouchProductModel> scrapeFromWeb(String websiteUrl) {
        try {
            List<TouchProductModel> productList = new ArrayList<>();
            boolean isLastPage = false;
            Integer pageNumber = 1;

            while (!isLastPage) {
                Document doc = Jsoup
                        .connect(String.format("%s?PAGEN_1=%d", websiteUrl, pageNumber))
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                        .header("Accept-Language", "*")
                        .timeout(10000)
                        .get();

                Elements products = doc.select("div.item").select(".product");

                for (Element product : products) {
                    TouchProductModel touchProductModel = new TouchProductModel();

                    if (!product.select("a.name").isEmpty()) {
                        touchProductModel.productName = product.select("a.name").select("span").text();
                        touchProductModel.productLink = touchUrl + product.select("a.name").attr("href");
                        touchProductModel.oldPrice = product.select("div.old_price").select("span.discount").text();
                        touchProductModel.article = product.select("span.changeArticle").text();

                        productList.add(touchProductModel);
                    }
                }

                if (!doc.select("li.bx-pag-next").isEmpty()) {
                    Element nextPageButton = doc.select("li.bx-pag-next").first();
                    if (nextPageButton.select("a").isEmpty()) {
                        isLastPage = true;
                    }
                }
                else {
                    isLastPage = true;
                }

                pageNumber++;
            }

            return productList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
