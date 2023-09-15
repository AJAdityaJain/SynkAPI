package com.synk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.regex.Pattern;

@SpringBootApplication
@OpenAPIDefinition
public class Application {
    public static final String ANSI_RESET = "\u001B[0m";public static final String ANSI_BLACK = "\u001B[30m";public static final String ANSI_RED = "\u001B[31m";public static final String ANSI_GREEN = "\u001B[32m";public static final String ANSI_YELLOW = "\u001B[33m";public static final String ANSI_BLUE = "\u001B[34m";public static final String ANSI_PURPLE = "\u001B[35m";public static final String ANSI_CYAN = "\u001B[36m";public static final String ANSI_WHITE = "\u001B[37m";

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static void main(String[] args) {

        System.out.println(Application.ANSI_RED +  "Starting SynkAPI..." + Application.ANSI_RESET);

//        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//            try {
//                Desktop.getDesktop().browse(new URI("http://localhost:8080/swagger-ui/index.html#/"));
//            } catch (IOException | URISyntaxException e) {
//                throw new RuntimeException(e);
//            }
//        }

        SpringApplication.run(Application.class, args);
    }
}
