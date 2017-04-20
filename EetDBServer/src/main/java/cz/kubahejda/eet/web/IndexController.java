package cz.kubahejda.eet.web;

import cz.kubahejda.eet.services.DBUserService;
import cz.kubahejda.eet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Kuba on 30.3.2017.
 */
@Controller
public class IndexController {



    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
