package cz.kubahejda.eet.services;

import com.sun.org.apache.xerces.internal.xs.StringList;
import cz.kubahejda.eet.model.Receipt;
import cz.kubahejda.eet.model.User;
import cz.kubahejda.eet.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Kuba on 30.3.2017.
 */
@Service
public class TransactionsService implements Transactions {
    public static final Logger LOGGER = LoggerFactory.getLogger(TransactionsService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public List<Receipt> getReceiptsForUser(String username) {
        User user = userService.getInfo(username).get(0);
        LOGGER.info("getting receipts for user: {}, {}", user.getUsername(), user.getCompanyId());
        return receiptRepository.find(user.getCompanyId());
    }

    @Override
    public String makeTransaction(HttpServletRequest request) {
        LOGGER.info("making transaction: {}, {}",request.getParameter("username"), request.getParameter("value"));
        User user = userService.getInfo(request.getParameter("username")).get(0);
        LOGGER.info("user {}", user.getVatId());
        StringBuilder sb = new StringBuilder("value=");
        sb.append(request.getParameter("value"));sb.append("&");
        sb.append("ico=");sb.append(user.getCompanyId());sb.append("&");
        sb.append("dic=");sb.append(user.getVatId());

        String resp = getResponseFromPHPServer(sb.toString());
        LOGGER.info("response: {}", resp);
        String[] data = resp.split(";");
        if (data[0] == null)
            return null;
        Receipt receipt = new Receipt(user.getCompanyId(), data[1], data[2], data[0],  data[3]);
        receiptRepository.create(receipt);
        return resp;
    }



    public static String getResponseFromPHPServer(String params) {
        String result = "";
        try {
            URL urlObj = new URL("http://localhost/eet/php-eet/simple.php?" + params);
            LOGGER.info("request: {}","http://localhost/eet/php-eet/simple.php?"+params);
            URLConnection serverConnection = urlObj.openConnection();

            String data = URLEncoder.encode("yourdata", "UTF-8");
            serverConnection.setDoOutput(true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(serverConnection.getOutputStream());
            streamWriter.write(data);
            streamWriter.flush();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            String line = "";
            result = "";

            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            streamWriter.flush();
            streamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
