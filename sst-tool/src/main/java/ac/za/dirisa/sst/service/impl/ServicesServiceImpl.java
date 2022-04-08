package ac.za.dirisa.sst.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.za.dirisa.sst.model.Services;
import ac.za.dirisa.sst.repository.ServicesRepository;
import ac.za.dirisa.sst.service.ServicesService;

@Service
public class ServicesServiceImpl implements ServicesService{

    @Autowired
    ServicesRepository repository;

    @Override
    public List<Services> services() {
        return repository.findAll();
    }
}
