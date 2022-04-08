package ac.za.dirisa.sst.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.za.dirisa.sst.model.User;
import ac.za.dirisa.sst.service.UserService;

@RestController
@RequestMapping("api/userservice")
public class UserServiceApiController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public List<User> findUsers(){
        return userService.getUsers();
    }
}
