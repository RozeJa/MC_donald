package demo;

import java.util.Map;
import java.util.TreeMap;

/**
 * RestProxy je třída, která umí získat ze vzdáleného zdroje REST data a převést je na oběkty
 */
public class RestProxy {
    // potřebuju vědět odkud
    private String source;

    private Map<String, EndPoint> endPoints = new TreeMap<>();

    public RestProxy(String source) {
        this.source = source;
    }

    /**
     * Odpovídá voání HTTP metody get
     */
    public <E> E get(Class<E> type) {
        return null;
    }

    /**
     * Odpovídá voání HTTP metody get
     * @param id parametr doplní za nastavený endpoint ještě jaký koliv další string. Ale to má využití spíše jen pro Id
     */
    public <E> E get(Class<E> type, String id) throws Exception {
        return null;
    }

    /**
     * Odpovídá voání HTTP metody post
     * Metoda očekává, že má nastavený endpoint pro třídy E
     * @param data instance třídy E se pošle v těle post requestu
     * 
     */
    public <E> E post(E data) throws Exception {
        return data;
    }

    /**
     * Odpovídá voání HTTP metody put
     */
    public <E> E put(E data) throws Exception {
        return data;
    }

    /**
     * Odpovídá voání HTTP metody patch
     */
    public <E> E patch(E data) throws Exception {
        return data;
    }

    /**
     * Odpovídá voání HTTP metody delete
     */
    public <E> E delete(E data) throws Exception {
        return data;
    }

    /**
     * Metoda připojí známé připojení k HTTP metodě a třídě, do které se mají data ze vzdáleného servru nahrát
     * @param <E> Třída, do jejíchž instancí se mají načíst data z requestu
     * @param method HTTP metoda z výčtového typu {@code HTTP_METHOD}
     * @param type Instance třídy popisující třídu, jenž má být navrácena
     * @param endpoint Cesta endpointu, která se přidá ke zdroji
     * @throws Exception
     */
    public <E> void mountEndpoint(HTTP_METHOD method, Class<E> type, String endpoint) throws Exception {
        EndPoint e = new EndPoint(method, endpoint, type);

        // Pokud už máš nastaveno jak pomocí dané metody zísmt daná data, tak vrať vyjímku
        if (endPoints.get(e.getKey()) != null) 
            throw new Exception("Pro potodu: " + method.getMethod() + " a třídu: " + type.getName() + " již byl přístup nastaven.");

        endPoints.put(e.getKey(), e);
    }

    public <E> void unmountEndpoint(HTTP_METHOD method, Class<E> type) {
        EndPoint endPoint = new EndPoint(method, "", type);
        endPoints.remove(endPoint.getKey());
    }
    
    /**
     * Výčtový typ obsahující HTTP metody
     */
    public enum HTTP_METHOD {
        GET("GET"), POST("POST"), PUT("PUT"), PATCH("PATCH"),   DELETE("DELETE");

        private String method;

        private HTTP_METHOD(String method) {
            this.method = method;
        }

        public String getMethod() {
            return method;
        }
    }
}
