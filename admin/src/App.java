
import java.rmi.Naming;

import javax.swing.JFrame;

import data.DB;
import data.models.Category;
import data.models.Improvement;
import data.models.Order;
import data.models.Product;
import gui.MainFrame;
import restApi.RestApi;

public class App {
    
    public static void main(String[] args) throws Exception {
        
        RestApi restApi = new RestApi("http://localhost:8080/api/");

        mountEndpoints(restApi);

        DB db = new DB(restApi);

        MainFrame mf = new MainFrame(db, "MC donald");

        setFrame(mf);
    }
    
    // zobrazení okna
    private static void setFrame(JFrame frame) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    // metoda zodpovídá za nastvení endpointů pro rest api 
    // mountpointy by bolo možné načítat ale...
    private static void mountEndpoints(RestApi restApi) throws Exception {
        // Categorie
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GETALL, Category.class, "categories/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GET, Category.class, "categories/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.POST, Category.class, "categories/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.PUT, Category.class, "categories/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.DELETE, Category.class, "categories/");

        // Produkty
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GETALL, Product.class, "products/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GET, Product.class, "products/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.POST, Product.class, "products/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.PUT, Product.class, "products/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.DELETE, Product.class, "products/");
        
        // Vylepšení
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GETALL, Improvement.class, "improvements/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GET, Improvement.class, "improvements/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.POST, Improvement.class, "improvements/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.PUT, Improvement.class, "improvements/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.DELETE, Improvement.class, "improvements/");

        // Objednávky
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GETALL, Order.class, "orders/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.GET, Order.class, "orders/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.POST, Order.class, "orders/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.PUT, Order.class, "orders/");
        restApi.mountEndpoint(RestApi.HTTP_METHOD.DELETE, Order.class, "orders/");
    }
}
