package cz.kubahejda.eet.services;

import cz.kubahejda.eet.model.Receipt;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kuba on 30.3.2017.
 */
public interface Transactions {
    public String makeTransaction(HttpServletRequest request);

    public List<Receipt> getReceiptsForUser(String username);
}
