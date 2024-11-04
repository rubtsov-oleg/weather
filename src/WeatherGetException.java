public class WeatherGetException extends RuntimeException {
    public WeatherGetException(String message) {
        super(message);
    }

    public WeatherGetException(String message, Throwable cause) {
        super(message, cause);
    }
}
