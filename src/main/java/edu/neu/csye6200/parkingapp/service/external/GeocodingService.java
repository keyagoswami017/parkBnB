package edu.neu.csye6200.parkingapp.service.external;
import edu.neu.csye6200.parkingapp.model.ParkingLocation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class GeocodingService {

    private static final String API_KEY = "9f6199ff4c0544ca95561fa87a4b3069";
    private static final String BASE_URL = "https://api.opencagedata.com/geocode/v1/json";

    public ParkingLocation getCoordinates(ParkingLocation location) {
        String query = String.format("%s, %s, %s, %s",
                location.getStreet(), location.getCity(), location.getState(), location.getCountry());

        String url = String.format("%s?key=%s&q=%s", BASE_URL, API_KEY, query);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.getJSONArray("results").length() > 0) {
            JSONObject coordinates = jsonResponse.getJSONArray("results")
                    .getJSONObject(0).getJSONObject("geometry");

            location.setLatitude(String.valueOf(coordinates.getDouble("lat")));
            location.setLongitude(String.valueOf(coordinates.getDouble("lng")));
        } else {
            throw new RuntimeException("Coordinates not found for the given address.");
        }

        return location;
    }
}
