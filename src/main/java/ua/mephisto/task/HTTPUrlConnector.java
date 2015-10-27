package ua.mephisto.task;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.mephisto.task.domain.City;
import ua.mephisto.task.domain.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by mephisto on 27.10.15.
 */
@Component
public class HTTPUrlConnector {

    @Value("${app.key:$1$12309856$euBrWcjT767K2sP9MHcVS/}")
    private String key;
    @Value("${app.url:$http://tripcomposer.net/rest/test/countries/get}")
    private String url;

    public List<Country> sendPost() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

        String connectId = "asd";
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(getJSONforPOST(connectId));
        wr.flush();
        wr.close();

        int responceCode = con.getResponseCode();
        StringBuilder sb = new StringBuilder();
        if (responceCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

        } else {
            throw new IOException("Cann't get data from server");
        }
        return parseJSONtoCountry(sb.toString());
    }

    public String getJSONforPOST(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.createObjectNode();
        root.put("key", this.key);
        root.put("echo", value);
        return root.toString();
    }

    public List<Country> parseJSONtoCountry(String value) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(value);
        String responceId = root.get("echo").asText();
        List<Country> countries = objectMapper.readValue(root.get("countries"),
                new TypeReference<List<Country>>() {
                });
        return countries;
    }
}
