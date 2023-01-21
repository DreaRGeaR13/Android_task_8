package kz.talipovsn.rates;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// СОЗДАТЕЛЬ КОТИРОВОК ВАЛЮТ
public class RatesReader {

    private static final String BASE_URL = "https://github.com/proffix4?tab=followers"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("Данные о фоловерах:\n"); // Считываем заголовок страницы
            data.append("\n");
            String e = doc.select("a.Link--secondary:nth-child(1)[href=\"https://github.com/proffix4?tab=followers\"] span").get(0).text();
            data.append("количество фолловеров: "+e);
            data.append("\n");
            Element x = doc.select("#user-profile-frame").get(0);
            for (Element row : x.select(".d-table")) {
                String s = row.select("div:nth-child(2) a span:nth-child(2)").get(0).text();
                data.append(s);
                data.append("\n");
            }
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}