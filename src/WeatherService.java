import java.util.Optional;

import com.google.gson.*;

public class WeatherService {
    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public void printTodayWeather(Double lat, Double lon) {
        Optional<String> data = weatherClient.getWeather(lat, lon, 1);
        data.ifPresent(System.out::println);
    }

    public void printTodayTemperature(Double lat, Double lon) {
        Optional<String> data = weatherClient.getWeather(lat, lon, 1);
        if (data.isPresent()) {
            JsonObject jsonObject = JsonParser.parseString(data.get()).getAsJsonObject();
            JsonObject factObject = jsonObject.getAsJsonObject("fact");
            int temperature = factObject.get("temp").getAsInt();
            System.out.println("Температура: " + temperature);
        } else {
            System.out.println("Погодные данные недоступны.");
        }
    }

    public void printAverageTemperature(Double lat, Double lon, Integer limit) {
        Optional<String> data = weatherClient.getWeather(lat, lon, limit);
        if (data.isPresent()) {
            double totalTemperature = 0;
            int count = 0;
            JsonObject jsonObject = JsonParser.parseString(data.get()).getAsJsonObject();
            JsonArray forecastsObject = jsonObject.getAsJsonArray("forecasts");
            for (JsonElement forecastElement : forecastsObject) {
                JsonObject forecastObject = forecastElement.getAsJsonObject();
                JsonObject partsObject = forecastObject.getAsJsonObject("parts");
                JsonObject dayObject = partsObject.getAsJsonObject("day");
                int temperature = dayObject.get("temp_avg").getAsInt();
                totalTemperature += temperature;
                count++;
            }
            double averageTemperature = count > 0 ? totalTemperature / count : 0;
            System.out.println("Средняя температура: " + averageTemperature);
        } else {
            System.out.println("Погодные данные недоступны.");
        }
    }
}
