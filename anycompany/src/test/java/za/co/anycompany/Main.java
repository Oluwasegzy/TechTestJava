package za.co.anycompany;


import java.util.Date;
import java.util.List;
import za.co.anycompany.model.Customer;
import za.co.anycompany.model.Order;
import za.co.anycompany.service.CustomerService;
import za.co.anycompany.service.OrderService;

public class Main {

    public static void main(String[] args) {
      long customerId = 1L;
       testSaveCustomer(mockCustomer(customerId, "SA", "Abel"));
      testPlaceOrder(mockOrder(customerId, 1L,28.9, 1.4));
       testCustomerRetrieveWithOrders(1L);
      testCustomerRetrieveAllWithOrders();
    }

  private static Customer mockCustomer(long customerId,String country,String name) {
      Customer customer = new Customer();
      customer.setCustomerId(customerId);
      customer.setCountry(country);
      customer.setDateOfBirth(new Date());
      customer.setName(name);
      return customer;

  }

  private static Order mockOrder(long customerId,long orderId, double amount,double vat) {
    Order order = new Order();
    order.setCustomerId(customerId);
    order.setOrderId(orderId);
    order.setAmount(amount);
    order.setVAT(vat);
    return order;

  }


  private static void testSaveCustomer(Customer i) {
    CustomerService customerService = new CustomerService();
    try {
      customerService.save(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static void testPlaceOrder(Order order) {
    OrderService orderService = new OrderService();
    try {
      orderService.placeOrder(order, order.getCustomerId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static void testCustomerRetrieveWithOrders(long i) {
        CustomerService customerService = new CustomerService();
      final Customer customerWithOrders = customerService.getCustomerWithOrders(i);
    System.out.println("======retrieve customer with customerId:" + i + "===============");
      System.out.println("customerId:" + customerWithOrders.getCustomerId());
      System.out.println("customerCountry:" + customerWithOrders.getCountry());
      System.out.println("customerName:" + customerWithOrders.getName());
      System.out.println("customerDOB:" + customerWithOrders.getDateOfBirth());
      System.out.println("orders:" + customerWithOrders.getOrders().size());
    }


  private static void testCustomerRetrieveAllWithOrders() {
      CustomerService customerService = new CustomerService();
    final List<Customer> allCustomerWithOrders = customerService.getAllCustomerWithOrders();
    System.out.println("======retrieve all customers ================");
    for (Customer customerWithOrders: allCustomerWithOrders) {
      System.out.println("customerId:" + customerWithOrders.getCustomerId());
      System.out.println("customerCountry:" + customerWithOrders.getCountry());
      System.out.println("customerName:" + customerWithOrders.getName());
      System.out.println("customerDOB:" + customerWithOrders.getDateOfBirth());
      System.out.println("orders:" + customerWithOrders.getOrders().size());
      System.out.println(" ================");
    }
  }


}
