package kz.talipovsn.jsoup_micro;

import android.os.Bundle;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView; // Иконка погоды
    private TextView textView; // Компонент для данных погоды

    private static final String BASE_URL = "https://github.com/proffix4?tab=followers"; // Адрес с котировками

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textView = findViewById(R.id.textView);

        onClick(null); // Нажмем на кнопку "Обновить"
    }

    // Кнопка "Обновить"
    public void onClick(View view) {
        textView.setText(R.string.not_data);
        try {

            StringBuilder data = new StringBuilder();
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
            textView.setText(data.toString().trim());
        } catch (Exception e) {
            textView.setText(R.string.error);
        }
    }
}
