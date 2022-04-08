package ac.za.dirisa.sst.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.za.dirisa.sst.model.Services;
import ac.za.dirisa.sst.service.ServicesService;

@RestController
@RequestMapping("api/service")
public class ServicesApiController {

    @Autowired
    ServicesService service;

    @GetMapping("/services")
    public List<Services> findServices(){
        return service.services();
    }
}
