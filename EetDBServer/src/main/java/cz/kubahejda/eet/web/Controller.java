package cz.kubahejda.eet.web;

import com.fasterxml.jackson.annotation.JsonView;
import cz.kubahejda.eet.model.Receipt;
import cz.kubahejda.eet.model.Registration;
import cz.kubahejda.eet.model.User;
import cz.kubahejda.eet.services.DBUserService;
import cz.kubahejda.eet.services.PrintService;
import cz.kubahejda.eet.services.Transactions;
import cz.kubahejda.eet.services.UserService;
import cz.kubahejda.eet.web.jsonview.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kuba on 23.3.2017.
 */

@RestController
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private DBUserService dbUserService;

    @Autowired
    private PrintService printService;

    @Autowired
    private Transactions transactions;

    @Autowired
    private String getKey;


    @RequestMapping(value = "/server/authenticate", produces = "text/plain")
    @ResponseBody
    public String authenticate(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        return new String("" + userService.authenticate(username, password));
    }

    @RequestMapping(value = "/server/authenticateUnencrypted")
    @ResponseBody
    public String authenticateUnencrypted(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        return new String("" + dbUserService.authenticate(username, dbUserService.encryptMessage(password, getKey)));
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/server/getInfo")
    public List<User> getInfo(@RequestParam(required = true) String username, @RequestParam(required = true) String password) {
        return dbUserService.getInfo(username);
    }

    @RequestMapping(value = "/server/makeTransaction", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String makeTransaction(HttpServletRequest request) {
        return transactions.makeTransaction(request);
    }

    @JsonView(Views.Public.class)
    @RequestMapping(value = "/server/getReceiptsForUser")
    @ResponseBody
    public List<Receipt> getReceiptsForUser(@RequestParam String username, @RequestParam String password) {
        return transactions.getReceiptsForUser(username);
    }

    @RequestMapping(value = "/server/registration", method = RequestMethod.GET)
    @ResponseBody
    public String registration(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Long companyId,
            @RequestParam String email,
            @RequestParam String companyName,
            @RequestParam String vatId) {
        if (!isValidEmailAddress(email))
            return (new Registration(1, "")).toString();
        return dbUserService.registration(username, password, companyId, email, companyName, vatId).toString();
    }

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    @ResponseBody
    public String print(@RequestParam String username, @RequestParam String date) {
        return printService.printRequest(username, date);
    }

    @RequestMapping(value = "/mojeuctenka/{ico}/{number}", method = RequestMethod.GET)
    @ResponseBody
    public String printById(@PathVariable(value = "ico") Long username, @PathVariable(value = "number") String date) {
        return printService.printIdRequest(username, date);
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
