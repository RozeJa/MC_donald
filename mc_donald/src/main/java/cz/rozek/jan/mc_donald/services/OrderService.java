package cz.rozek.jan.mc_donald.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.rozek.jan.mc_donald.models.Order;
import cz.rozek.jan.mc_donald.repositories.OrderRepository;

@Service
public class OrderService implements CrudService<Order, String> {
   
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order create(Order order) throws ValidationException, DuplicateKeyException {
        validate(order);

        order.setId(null);
        Order o = orderRepository.save(order);
        return o;
    }

    @Override
    public Order read(String id) {
        Order o = orderRepository.findById(id).get();
        return o;
    }

    @Override
    public List<Order> readAll() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public Order update(String id, Order order) throws ValidationException, DuplicateKeyException {
        validate(order);

        order.setId(id);

        Order updated = orderRepository.save(order);
        return updated;
    }

    @Override
    public Order delete(String id) {
        Order o = orderRepository.findById(id).get();
        o.setAvailable(false);
        
        orderRepository.save(o);
        return o;
    }


    @Override
    public void validate(Order order) throws ValidationException {
        StringBuilder sb = new StringBuilder();

        if (order.getProducts().size() == 0) {
            sb.append("Order contains no products.\n");
        }

        if (!sb.toString().isEmpty()) {
            throw new ValidationException(sb.toString());
        }
    }
}
