package com.synk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synk.database.DatabaseConnector;
import com.synk.websocket.SocketServer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.regex.Pattern;

@SpringBootApplication
@OpenAPIDefinition
public class Application {
    public static final String ANSI_RESET = "\u001B[0m";public static final String ANSI_BLACK = "\u001B[30m";public static final String ANSI_RED = "\u001B[31m";public static final String ANSI_GREEN = "\u001B[32m";public static final String ANSI_YELLOW = "\u001B[33m";public static final String ANSI_BLUE = "\u001B[34m";public static final String ANSI_PURPLE = "\u001B[35m";public static final String ANSI_CYAN = "\u001B[36m";public static final String ANSI_WHITE = "\u001B[37m";
    public static DatabaseConnector db;
    public static SocketServer ws;
    public static SpringApplication api;
    public static ObjectMapper json;

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static void main(String[] args) {


        json = new ObjectMapper();

        System.out.println(Application.ANSI_RED +  "Connecting to MySQL" + Application.ANSI_RESET);
        db = new DatabaseConnector();

        System.out.println(Application.ANSI_GREEN +  "Starting Websocket Server" + Application.ANSI_RESET);
        ws = new SocketServer(8887);ws.start();

        System.out.println(Application.ANSI_BLUE +  "Starting SynkAPIs" + Application.ANSI_RESET);
        api = new SpringApplication(Application.class);api.run(args);
    }
}
