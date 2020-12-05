package za.co.anycompany.service;

import za.co.anycompany.datalayer.CustomerRepository;
import za.co.anycompany.datalayer.OrderRepository;
import za.co.anycompany.model.Customer;
import za.co.anycompany.model.Order;

public class OrderService {

    private OrderRepository orderRepository = new OrderRepository();

    public void placeOrder(Order order, long customerId) throws Exception
    {
        if (order.getOrderId() == 0) {
            throw new Exception("order id is mandatory");
        }
        Customer customer = CustomerRepository.load(customerId, true);
        if (customer.getCustomerId() == 0) {
            throw new Exception("customer id is mandatory");
        }
        order.setCustomerId(customer.getCustomerId());
        if (order.getAmount() == 0)
            throw new Exception("valid amount is required");

        if (customer.getCountry() == "UK")
            order.setVAT(0.2d);
        else
            order.setVAT(0);

        orderRepository.save(order);
    }
}
