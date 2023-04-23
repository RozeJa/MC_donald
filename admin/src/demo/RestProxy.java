package demo;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * RestProxy je třída, která umí získat ze vzdáleného zdroje REST data a převést je na oběkty
 */
public class RestProxy {
    // potřebuju vědět odkud
    private URI source;

    private Map<HTTP_METHOD, Map<Class, String>> endpoints = new HashMap<>();

    public RestProxy(URI source) {
        this.source = source;

        endpoints.put(HTTP_METHOD.GET, new HashMap<>());
        endpoints.put(HTTP_METHOD.POST, new HashMap<>());
        endpoints.put(HTTP_METHOD.PUT, new HashMap<>());
        endpoints.put(HTTP_METHOD.PATCH, new HashMap<>());
        endpoints.put(HTTP_METHOD.DELETE, new HashMap<>());
    }

    public <E> E get(Class<E> type) throws Exception {


        return ((E) type.getConstructor().newInstance());
    }

    public <E> E post(E data) throws Exception {
        return data;
    }

    public <E> E put(E data) throws Exception {
        return data;
    }

    public <E> E patch(E data) throws Exception {
        return data;
    }

    public <E> E delete(E data) throws Exception {
        return data;
    }

    public <E> void registerEndpoint(HTTP_METHOD method, Class<E> type, String endpoint) throws Exception {
        endpoints.get(method).put(type, endpoint);
    }
    

    public enum HTTP_METHOD {
        GET("GET"), POST("POST"), PUT("PUT"), PATCH("PATCH"), DELETE("DELETE");

        private String method;

        private HTTP_METHOD(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }
}
