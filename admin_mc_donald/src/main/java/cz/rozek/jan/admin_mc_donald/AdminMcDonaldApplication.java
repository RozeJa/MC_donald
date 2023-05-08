package cz.rozek.jan.admin_mc_donald;

import javax.swing.JFrame;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import cz.rozek.jan.admin_mc_donald.data.DB;
import cz.rozek.jan.admin_mc_donald.data.OrderStompSessionHandler;
import cz.rozek.jan.admin_mc_donald.data.models.Category;
import cz.rozek.jan.admin_mc_donald.data.models.Improvement;
import cz.rozek.jan.admin_mc_donald.data.models.Order;
import cz.rozek.jan.admin_mc_donald.data.models.Product;
import cz.rozek.jan.admin_mc_donald.gui.MainFrame;
import cz.rozek.jan.admin_mc_donald.restApi.RestApi;


@SpringBootApplication
public class AdminMcDonaldApplication {

	public static void main(String[] args) throws Exception {

		// připojení přes websocket na server
		WebSocketClient client = new StandardWebSocketClient();

		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		
		OrderStompSessionHandler sessionHandler = new OrderStompSessionHandler();
		stompClient.connectAsync("ws://localhost:8080/ws", sessionHandler);


        // nastevení získávání dat ze servru
        RestApi restApi = new RestApi("http://localhost:8080/api/");
        mountEndpoints(restApi);
        DB db = new DB(restApi);

		// vytvoření a otevření gui
        MainFrame mf = new MainFrame(db, "MC donald");
		sessionHandler.setMainFrame(mf);
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
