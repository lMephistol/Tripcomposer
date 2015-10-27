package ua.mephisto.task;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mephisto.task.model.City;
import ua.mephisto.task.model.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Class for get information from server and retrieve into Java class.
 *
 * Created by mephisto on 27.10.15.
 */
@Component("connector")
public class CountryRequest {

    @Value("${app.key:$1$12309856$euBrWcjT767K2sP9MHcVS/}")
    private String key;
    @Value("${app.url:$http://tripcomposer.net/rest/test/countries/get}")
    private String url;

    /**
     * Send POST to server.
     * @return List<Country>
     * @throws IOException
     */
    public List<Country> send() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

        //Id for request.
        String requestId = "some123";
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(getJSONforPOST(requestId));
        wr.flush();
        wr.close();

        int responceCode = con.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (responceCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

        } else {
            throw new IOException("Cann't get data from server");
        }
        return setCityCountry(parseJSONtoCountry(sb.toString(), requestId));
    }

    // Add relationship between country and city
    private List<Country> setCityCountry(List<Country> countries){
        for(Country country: countries){
            for(City city : country.getCities()){
                city.setCountry(country);
            }
        }
        return countries;
    }

    /**
     * Generate our JSON request.
     * @param value request id.
     * @return JSON
     */
    public String getJSONforPOST(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("key", this.key);
        root.put("echo", value);
        return root.toString();
    }

    /**
     * Parse JSON from response to objects.
     * @param JSONvalue
     * @param requestId
     * @return List<Country>
     * @throws IOException if can't parse JSON
     */
    public List<Country> parseJSONtoCountry(String JSONvalue,String requestId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(JSONvalue);
        String responseId = root.get("echo").asText();
        //I'm not sure that it is correct to throw exception.
        if(!responseId.equals(requestId)){
            throw new IOException("Oops, it isn't right response.");
        }
        List<Country> countries = objectMapper.readValue(root.get("countries"),
                new TypeReference<List<Country>>() {
                });
        return countries;
    }
}
