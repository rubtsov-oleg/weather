import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import io.netty.handler.codec.http.HttpResponseStatus;

public class WeatherClient {
    private final String url;
    private final HttpClient client;
    private final String apiToken;

    public WeatherClient(String url, String apiToken) {
        this.url = url;
        client = HttpClient.newHttpClient();
        this.apiToken = apiToken;
    }

    public Optional<String> getWeather(Double lat, Double lon, Integer limit) {
        StringBuilder path = new StringBuilder("/v2/forecast?hours=false&extra=false");
        if (lat != null && lon != null) {
            path.append("&lat=").append(lat).append("&lon=").append(lon);
        }
        if (limit != null) {
            path.append("&limit=").append(limit);
        }

        URI uri = URI.create(this.url + path);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("X-Yandex-Weather-Key", apiToken)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpResponseStatus.OK.code()) {
                return Optional.of(new String(response.body().getBytes(), StandardCharsets.UTF_8));
            } else if (response.statusCode() == HttpResponseStatus.NOT_FOUND.code()) {
                return Optional.empty();
            } else {
                throw new WeatherGetException("Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new WeatherGetException("Ошибка при выполнении запроса", e);
        }
    }
}
