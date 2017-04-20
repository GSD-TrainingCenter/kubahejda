package cz.kubahejda.eet.services;

import cz.kubahejda.eet.model.Receipt;
import cz.kubahejda.eet.model.User;
import cz.kubahejda.eet.repository.ReceiptRepository;
import cz.kubahejda.eet.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Kuba on 20.4.2017.
 */
@Service
public class PrintService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PrintService.class);

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    UserRepository userRepository;

    public String printRequest(String username, String date) {
        User user = userRepository.getInfo(username).get(0);
        Receipt receipt = receiptRepository.findByCompanyIdAndDate(user.getCompanyId(), date);

        String params = getParamsString(
                user.getCompanyName(),
                user.getCompanyId(),
                user.getVatId(),
                receipt.getDate(),
                receipt.getFik(),
                receipt.getBkp(),
                receipt.getValue(),
                "adresa");
        return getResponseFromPHPServer(params);
    }

    public String printIdRequest(Long companyId, String date) {

        User user = userRepository.getInfo(companyId);
        Receipt receipt = receiptRepository.findByCompanyIdAndDate(user.getCompanyId(), date);

        String params = getParamsString(
                user.getCompanyName(),
                user.getCompanyId(),
                user.getVatId(),
                receipt.getDate(),
                receipt.getFik(),
                receipt.getBkp(),
                receipt.getValue(),
                "adresa");
        return getResponseFromPHPServer(params);
    }


    public static String getParamsString(
            String companyName,
            Long companyId,
            String vatId,
            String date,
            String fik,
            String bkp,
            String value,
            String address) {
        StringBuilder sb = new StringBuilder();
        sb.append("company=").append(companyName.replace(' ', '+')).append("&");
        sb.append("ico=").append(companyId).append("&");
        sb.append("dic=").append(vatId).append("&");
        sb.append("date=").append(date).append("&");
        sb.append("fik=").append(fik).append("&");
        sb.append("bkp=").append(bkp).append("&");
        sb.append("value=").append(value).append("&");
        sb.append("address=").append(address.replace(' ', '+'));

        return sb.toString();
    }


    public static String getResponseFromPHPServer(String params) {
        String result = "";
        try {
            URL urlObj = new URL("http://localhost/eet/php-eet/print.php?" + params);
            LOGGER.info("http://localhost/eet/php-eet/print.php?" + params);
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
