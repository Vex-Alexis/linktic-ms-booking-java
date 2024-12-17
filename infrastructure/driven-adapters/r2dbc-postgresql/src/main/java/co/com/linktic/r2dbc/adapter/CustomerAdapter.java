package co.com.linktic.r2dbc.adapter;

import co.com.linktic.model.customer.Customer;
import co.com.linktic.model.customer.gateways.CustomerDBGateway;
import co.com.linktic.r2dbc.entity.CustomerEntity;
import co.com.linktic.r2dbc.helper.ReactiveAdapterOperations;
import co.com.linktic.r2dbc.repository.CustomerRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class CustomerAdapter extends ReactiveAdapterOperations<Customer, CustomerEntity, UUID, CustomerRepository> implements CustomerDBGateway {
    public CustomerAdapter(CustomerRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Customer.class));
    }


}
