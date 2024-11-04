public class Main {
    public static void main(String[] args) {
        WeatherClient weatherClient = new WeatherClient(
                "https://api.weather.yandex.ru", "780bcab9-7f45-4833-af9b-7da2c27e4146"
        );
        WeatherService weatherService = new WeatherService(weatherClient);

        weatherService.printTodayWeather(55.75, 37.62);
        weatherService.printTodayTemperature(55.75, 37.62);
        weatherService.printAverageTemperature(55.75, 37.62, 3);
    }
}