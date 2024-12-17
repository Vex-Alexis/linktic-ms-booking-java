package co.com.linktic.r2dbc.adapter;

import co.com.linktic.model.service.Service;
import co.com.linktic.model.service.gateways.ServiceDBGateway;
import co.com.linktic.r2dbc.entity.ServiceEntity;
import co.com.linktic.r2dbc.helper.ReactiveAdapterOperations;
import co.com.linktic.r2dbc.repository.ServiceRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ServiceAdapter extends ReactiveAdapterOperations<Service, ServiceEntity, UUID, ServiceRepository> implements ServiceDBGateway {
    public ServiceAdapter(ServiceRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Service.class));
    }
}
