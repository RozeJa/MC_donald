package data;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import data.models.Category;
import data.models.IData;
import data.models.Improvement;
import data.models.Order;
import data.models.Product;
import restApi.RestApi;

public class DB {
    private RestApi api;

    public DB(RestApi api) {
        this.api = api;
    }

    public RestApi getApi() {
        return api;
    }
    public void setApi(RestApi api) {
        this.api = api;
    }

    public <E extends IData> Map<String, E> getAll(Class<E> class1) { 
        try {
            List<E> allData = api.getAll(class1);

            return allData.stream().collect(Collectors.toMap(IData::getId, Function.identity()));
        } catch (Exception e) {
            return new TreeMap<>();
        }
    }
    public <E extends IData> E getOne(Class<E> class1, String id) {
        try {
            return api.get(class1, id);
        } catch (Exception e) {
            return null;
        }
    }

    public <E extends IData> E writeData(E data) {
        if (data.getId() == null) {
            try {
                return api.post(data);
            } catch (Exception e) {
                return null;
            }
        } else {   
            try {
                return api.put(data, data.getId());        
            } catch (Exception e) {
                return null;
            }
        }
    }
    public <E extends IData> E delete(E data) {
        try {
            return api.delete(data, data.getId());
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Class<? extends IData>> getDataTypes() {
        Map<String, Class<? extends IData>> options = new TreeMap<>();

        options.put("Produkty", Product.class);
        options.put("Vylepšení", Improvement.class);
        options.put("Kategorie", Category.class);
        options.put("Objedávky", Order.class);

        return options;
    }
}
