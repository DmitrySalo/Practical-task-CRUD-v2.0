package com.example.webappcrud;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

@Log
@SpringBootApplication
public class WebAppCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppCrudApplication.class, args);
    }

    @EventListener({ApplicationReadyEvent.class})
    public void applicationReadyEvent() {
        log.info("Application was started... Launching browser page...");
        browse("http://localhost:8080/login");
    }

    public static void browse(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();

            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}