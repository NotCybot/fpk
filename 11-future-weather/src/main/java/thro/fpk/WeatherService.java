package thro.fpk;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.*;

public class WeatherService {

    WeatherService() {

    }

    //our locations of interest, for an average of the western european weather
    // Rosenheim, Germany (47.8567, 12.1225)
    // Brest, France (48.3903, -4.4860)
    // Oslo, Norway (59.9139, 10.7461)

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public Future<Integer> fetchTemperature(String latitude, String longitude) {
        return executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                String jsonResponse = ApiClient.fetchWeatherData(latitude, longitude);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return jsonNode.get("current_weather").get("temperature").asInt();
            }
        });
    }

    public CompletableFuture<Integer>  fetchTemperatureAsync(String latitude, String longitude) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String jsonResponse = ApiClient.fetchWeatherData(latitude, longitude);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                return jsonNode.get("current_weather").get("temperature").asInt();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Double> fetchAverageTemperature() {
        String[][] locations = {
                {"47.8567", "12.1225"},
                {"48.3903", "-4.4860"},
                {"59.9139", "10.7461"}
        };


        CompletableFuture<Integer> rosenheimTemperature = fetchTemperatureAsync(locations[0][0], locations[0][1]);
        CompletableFuture<Integer> brestTemperature = fetchTemperatureAsync(locations[1][0], locations[1][1]);
        CompletableFuture<Integer> osloTemperature = fetchTemperatureAsync(locations[2][0], locations[2][1]);

        return CompletableFuture.allOf(rosenheimTemperature, brestTemperature, osloTemperature)
                .thenApply(v -> {
                    try {
                        return (rosenheimTemperature.get() + brestTemperature.get() + osloTemperature.get()) / 3.0;
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public CompletableFuture<String> fetchWeatherDescriptionAsync(String latitude, String longitude) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String jsonResponse = ApiClient.fetchWeatherData(latitude, longitude);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                int weatherCode = rootNode.at("/current_weather/weathercode").asInt();
                return WmoCodes.getDescription(weatherCode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    public CompletableFuture<String> fetchWeatherSummary(String latitude, String longitude) {
        CompletableFuture<Integer> temperatureFuture = fetchTemperatureAsync(latitude, longitude);
        CompletableFuture<String> weatherDescriptionFuture = fetchWeatherDescriptionAsync(latitude, longitude);

        return temperatureFuture.thenCombine(weatherDescriptionFuture, (temperature, weatherDescription) -> {
            return "The weather is " + weatherDescription + " with a temperature of " + temperature + "°C";
        });
    }


    public static void main(String[] args) throws Exception {
        WeatherService weatherService = new WeatherService();
        String[][] locations = {
                {"47.8567", "12.1225"},
                {"48.3903", "-4.4860"},
                {"59.9139", "10.7461"}
        };

        Future<Integer> rosenheimTemperature = new WeatherService().fetchTemperature(locations[0][0], locations[0][1]);
        Future<Integer> brestTemperature = new WeatherService().fetchTemperature(locations[1][0], locations[1][1]);
        Future<Integer> osloTemperature = new WeatherService().fetchTemperature(locations[2][0], locations[2][1]);

        int rosenheimTemp = rosenheimTemperature.get();
        int brestTemp = brestTemperature.get();
        int osloTemp = osloTemperature.get();

        double averageTemp = (rosenheimTemp + brestTemp + osloTemp) / 3.0;
        System.out.println("Average temperature: " + averageTemp + "°C");

        weatherService.fetchAverageTemperature().thenAccept(averageTemperatureCompletableFuture -> {
            System.out.println("Average Temperature: " + averageTemperatureCompletableFuture + "°C");
        }).join();


        for (String[] location : locations) {
            weatherService.fetchWeatherSummary(location[0], location[1]).thenAccept(summary -> {
                System.out.println("Location (" + location[0] + ", " + location[1] + "): " + summary);
            }).join();
        }


        // access temperature data from the JSON response

    }
}
