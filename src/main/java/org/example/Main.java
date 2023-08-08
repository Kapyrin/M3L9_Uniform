package org.example;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {

        try {
            InetSocketAddress localhost = new InetSocketAddress("localhost", 8080);
            HttpServer httpServer = HttpServer.create(localhost, 0);

            httpServer.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    byte[] bytes = "Hello".getBytes();
                    exchange.sendResponseHeaders(200, bytes.length);

                    OutputStream responseBody = exchange.getResponseBody();
                    responseBody.write(bytes);
                    responseBody.close();

                }
            });

            httpServer.createContext("/some", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {

                    byte[] bytes = "UNIFORM".getBytes();
                    exchange.sendResponseHeaders(201, bytes.length);

                    OutputStream responseBody = exchange.getResponseBody();
                    responseBody.write(bytes);
                    responseBody.close();

                }
            });
            httpServer.createContext("/picture", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    File picture = new File("C:/Users/Vladimir/IdeaProjects/M3L9_Uniform/src/main/resources/green.jpg");

                    byte[] bytes = Files.readAllBytes(picture.toPath());
                    exchange.sendResponseHeaders(200, bytes.length);

                    OutputStream responseBody = exchange.getResponseBody();
                    responseBody.write(bytes);
                    responseBody.close();

                }
            });
            httpServer.createContext("/reverse", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String requestUri = exchange.getRequestURI().getPath();
                    String inputText = requestUri.substring( requestUri.lastIndexOf("/") +1);
                    String reverseText = new StringBuilder(inputText).reverse().toString();
                    byte [] bytes = reverseText.getBytes();
                    exchange.sendResponseHeaders(200, bytes.length);
                    OutputStream responseBody = exchange.getResponseBody();
                    responseBody.write(bytes);
                    responseBody.close();
                }
            });

            httpServer.start();

            // localhost:8080/
            // yandex.ru/

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}