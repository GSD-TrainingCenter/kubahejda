package cz.kubahejda.eet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by Kuba on 23.3.2017.
 */

@ComponentScan({"cz.kubahejda.eet.repository","cz.kubahejda.eet.services"})
@Import({DatabaseConfig.class})
public class AppConfig {
}
