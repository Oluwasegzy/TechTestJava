package za.co.anycompany.service;

import java.util.List;
import za.co.anycompany.datalayer.CustomerRepository;
import za.co.anycompany.datalayer.OrderRepository;
import za.co.anycompany.model.Customer;
import za.co.anycompany.model.Order;

public class CustomerService {

    private CustomerRepository customerRepository = new CustomerRepository();

    public Customer getCustomerWithOrders(long customerId)
    {
        return  CustomerRepository.load(customerId, false);

    }

    public List<Customer> getAllCustomerWithOrders()
    {
        return  CustomerRepository.loadAll(false);

    }

    public void save(Customer customer) throws Exception
    {
        if (customer.getCustomerId() == 0) {
            throw new Exception("customer id is mandatory");
        }
        final Customer foundCustomer = CustomerRepository.load(customer.getCustomerId(), true);
        if (foundCustomer.getCustomerId() != 0) {
            throw new Exception("customer with this id already exist");
        }
        customerRepository.save(customer);

    }
}
