package com.yeris.scrapeweblab;

import org.apache.poi.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
@RestController
public class ScrapeWebLabApplication {

    private static final String defaultUrl = "https://touch.com.ua/ua/";

    public static void main(String[] args) {
        SpringApplication.run(ScrapeWebLabApplication.class, args);
    }
    @GetMapping("/scrape")
    public @ResponseBody byte[] scrape(@RequestParam(value = "targetUrl") String targetUrl) throws IOException {
        DataScraper scraper = new DataScraper();
        List<TouchProductModel> productList = scraper.scrapeFromWeb(defaultUrl + targetUrl + "/");

        String excelFileName = ExcelReportGenerator.getExcelFromProductList(productList);

        return Files.readAllBytes(Paths.get(excelFileName));
    }

    @GetMapping(value = "/image")
    public @ResponseBody byte[] getImage() throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("products.xlsx");
        return IOUtils.toByteArray(in);
    }
}
