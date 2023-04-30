package restApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RestProxy je třída, která umí získat ze vzdáleného zdroje REST data a převést
 * je na oběkty
 */
public class RestApi {
    // potřebuju vědět odkud
    private String source;

    private Map<String, EndPoint> endPoints = new TreeMap<>();

    public RestApi(String source) {
        this.source = source;
    }

    /**
     * Odpovídá voání HTTP metody get
     */
    public <E> List<E> getAll(Class<E> type) throws EndpointNotFound, IOException, JsonMappingException {
        EndPoint<E> endPoint = getEndPoint(HTTP_METHOD.GETALL, type);

        String response = doRequest(HTTP_METHOD.GET, source + endPoint.getEndPoint(), "");

        return parseJsonValues(response, type);
    }

    /**
     * Odpovídá voání HTTP metody get
     * 
     * @param id parametr doplní za nastavený endpoint ještě jaký koliv další
     *           string. Ale to má využití spíše jen pro Id
     */
    public <E> E get(Class<E> type, String id) throws EndpointNotFound, IOException, JsonMappingException {
        EndPoint<E> endPoint = getEndPoint(HTTP_METHOD.GET, type);

        String response = doRequest(HTTP_METHOD.GET, source + endPoint.getEndPoint() + id, "");

        return parseJsonValue(response, type);
    }

    /**
     * Odpovídá voání HTTP metody post
     * Metoda očekává, že má nastavený endpoint pro třídy E
     * 
     * @param data instance třídy E se pošle v těle post requestu
     * 
     */
    public <E> E post(E data) throws EndpointNotFound, IOException, JsonMappingException {

        EndPoint<E> endPoint = getEndPoint(HTTP_METHOD.POST, (Class<E>) data.getClass());

        String jsonData = convertDataToJson(data);

        String response = doRequest(HTTP_METHOD.POST, source + endPoint.getEndPoint(), jsonData);

        return parseJsonValue(response, (Class<E>) data.getClass());
    }

    /**
     * Odpovídá voání HTTP metody put
     */
    public <E> E put(E data, String id) throws EndpointNotFound, IOException, JsonMappingException {

        EndPoint<E> endPoint = getEndPoint(HTTP_METHOD.PUT, (Class<E>) data.getClass());

        String jsonData = convertDataToJson(data);

        String response = doRequest(HTTP_METHOD.PUT, source + endPoint.getEndPoint() + id, jsonData);

        return parseJsonValue(response, (Class<E>) data.getClass());
    }

    /**
     * Odpovídá voání HTTP metody delete
     */
    public <E> E delete(E data, String id) throws EndpointNotFound, IOException, JsonMappingException {
        EndPoint<E> endPoint = getEndPoint(HTTP_METHOD.DELETE, (Class<E>) data.getClass());

        String jsonData = convertDataToJson(data);

        String response = doRequest(HTTP_METHOD.DELETE, source + endPoint.getEndPoint() + id, jsonData);

        return parseJsonValue(response, (Class<E>) data.getClass());
    }

    /**
     * Metoda připojí známé připojení k HTTP metodě a třídě, do které se mají data
     * ze vzdáleného servru nahrát
     * 
     * @param <E>      Třída, do jejíchž instancí se mají načíst data z requestu
     * @param method   HTTP metoda z výčtového typu {@code HTTP_METHOD}
     * @param type     Instance třídy popisující třídu, jenž má být navrácena
     * @param endpoint Cesta endpointu, která se přidá ke zdroji
     * @throws MountingEndPointExeption
     */
    public <E> void mountEndpoint(HTTP_METHOD method, Class<E> type, String endpoint) throws MountingEndPointExeption {
        EndPoint<E> e = new EndPoint<>(method, endpoint, type);

        // Pokud už máš nastaveno jak pomocí dané metody zísmt daná data, tak vrať
        // vyjímku
        if (endPoints.get(e.getKey()) != null)
            throw new MountingEndPointExeption(
                    "Pro potodu: " + method.getMethod() + " a třídu: " + type.getName() + " již byl přístup nastaven.");

        endPoints.put(e.getKey(), e);
    }

    public <E> void unmountEndpoint(HTTP_METHOD method, Class<E> type) {
        EndPoint<E> endPoint = new EndPoint(method, "", type);
        endPoints.remove(endPoint.getKey());
    }

    private String doRequest(HTTP_METHOD method, String endPoint, String body)
            throws MalformedURLException, IOException {

        URL url = new URL(endPoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.getMethod());

        if (method != HTTP_METHOD.GET) {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(connection.getOutputStream(), "UTF-8"))) {
                bw.write(body);
            } catch (Exception e) {
                throw new IOException("Nastal problém při odesílání dat na vzdálený bod.");
            }
        }

        StringBuilder content = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            throw new IOException("Nastal problém při čtení odpovědi.");
        }

        return content.toString();
    }

    private <E> EndPoint<E> getEndPoint(HTTP_METHOD method, Class<E> type) throws EndpointNotFound {
        EndPoint<E> endPoint = endPoints.get((new EndPoint<>(method, type)).getKey());

        if (endPoint == null)
            throw new EndpointNotFound("Endpoint is not mounted.");

        return endPoint;
    }

    private <E> E parseJsonValue(String json, Class<E> type) throws JsonProcessingException, JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, type);
    }

    private <E> List<E> parseJsonValues(String json, Class<E> type)
            throws JsonProcessingException, JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, new TypeReference<List<E>>() {
        }).stream().map(i -> objectMapper.convertValue(i, type)).toList();
    }

    private String convertDataToJson(Object data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(data);
    }

    /**
     * Výčtový typ obsahující HTTP metody a GETALL pro určení, že z endpointu se
     * načítá kolekce dat
     */
    public enum HTTP_METHOD {
        GET("GET"), GETALL("GETALL"), POST("POST"), PUT("PUT"), DELETE("DELETE");

        private String method;

        private HTTP_METHOD(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }
}
