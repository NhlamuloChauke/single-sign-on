package ac.za.dirisa.sst.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ac.za.dirisa.sst.dto.RegistrationRequest;
import ac.za.dirisa.sst.service.RegistrationService;

@RestController
@RequestMapping(path = "/api/v1/public/registration")
public class RegistrationApiController {

	@Autowired
    RegistrationService registrationService;

    @PostMapping("")
    public String register(@RequestBody RegistrationRequest registerRequest) {

        return registrationService.register(registerRequest);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {

       // return registrationService.confirmToken(token);
    	return "";
    }
}