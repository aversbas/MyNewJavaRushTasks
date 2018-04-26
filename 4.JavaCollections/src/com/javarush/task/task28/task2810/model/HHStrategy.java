package com.javarush.task.task28.task2810.model;


import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Alexey on 21.04.2017.
 */

public class HHStrategy implements Strategy {
    //private static final String URL_FORMAT = "https://hh.ua/search/vacancy";
    //private static final String URL_FORMAT = "https://hh.ua/search/vacancy?text=java+Junior+android&only_with_salary=false&enable_snippets=true&clusters=true&salary=";
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%D0%BA%D0%B8%D0%B5%D0%B2";
    //private static final String URL_FORMAT = "https://javarush.ru/search/vacancy?text=java+odessa&currency_code=UAH&clusters=true&page=1";

    protected Document getDocument(String searchString, int page) throws IOException {
        String url = String.format(URL_FORMAT, searchString, 1);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.167 Safari/537.36")
                .referrer("no-referrer-when-downgrade")
                .get();
        return doc;
    }

    @Override
    public List<Vacancy> getVacancies(String searchString) throws IOException {
        List<Vacancy> vacancies = new ArrayList<>();

        for (int i = 0; ; i++) {
            Document document = getDocument(URL_FORMAT, i);

            System.out.println(document);

            Elements plainVacancies = document.select("[data-qa=\"vacancy-serp__vacancy\"]");
            if (plainVacancies.size() == 0) {
                break;
            }
            Iterator iterator = plainVacancies.iterator();
            Element rawVacancy;
            while (iterator.hasNext()) {
                Vacancy vacancy = new Vacancy();
                rawVacancy = (Element) iterator.next();

                vacancy.setTitle(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-title\"]").first().text());

                Elements salaries = rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-compensation\"]");
                if (salaries.size() > 0) {
                    vacancy.setSalary(salaries.first().text());
                } else {
                    vacancy.setSalary("");
                }

                vacancy.setCity(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-address\"]").first().text());
                vacancy.setCompanyName(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-employer\"]").first().text());
                vacancy.setSiteName("hh.ru");
                vacancy.setUrl(rawVacancy.select("[data-qa=\"vacancy-serp__vacancy-title\"]").first().attr("href"));

                vacancies.add(vacancy);
            }
        }
        return vacancies;
    }

}