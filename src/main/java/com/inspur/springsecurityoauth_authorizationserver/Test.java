package com.inspur.springsecurityoauth_authorizationserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class Test {
    class RestGetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            OutputStream responseBody = he.getResponseBody();
            responseBody.write("hello world".getBytes());
            responseBody.close();
        }
    }
    public void serverStart() throws IOException {
        HttpServerProvider provider = HttpServerProvider.provider();
        HttpServer httpserver = provider.createHttpServer(new InetSocketAddress(8080), 100);
        httpserver.createContext("/restDemo", new RestGetHandler());
        httpserver.setExecutor(null);
        httpserver.start();
    }
    public static void main(String[] args) throws IOException {
        new Test().serverStart();
    }
}
