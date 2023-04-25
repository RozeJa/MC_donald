package demo;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
    
        RestProxy proxy = new RestProxy("http://localhost:8080/api/");

        proxy.mountEndpoint(RestProxy.HTTP_METHOD.GETALL, Category.class, "categories/");
        proxy.mountEndpoint(RestProxy.HTTP_METHOD.GET, Category.class, "categories/");

        List<Category> css = proxy.getAll(Category.class);

        Category c2 = proxy.get(Category.class, "644672645a434b1e5a653828");

        css.stream().forEach(System.out::println);


        proxy.mountEndpoint(RestProxy.HTTP_METHOD.POST, Category.class, "categories/");

        Category saved = proxy.post(new Category("Nápoje", true));
        System.out.println(saved.getId());
        System.out.println(saved.getId());
        System.out.println(saved.isAvailable());
    }
}
