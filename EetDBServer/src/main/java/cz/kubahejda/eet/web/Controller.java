package cz.kubahejda.eet.web;

import cz.kubahejda.eet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kuba on 23.3.2017.
 */

@RestController
public class Controller {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/server/authenticate", produces = "text/plain")
    @ResponseBody
    public String authenticate(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        return new String("" + userService.authenticate(username, password));
    }
}
