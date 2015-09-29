package com.example.helloworld.resources;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chris on 29/09/2015.
 */
@WebSocket
@Path("/wsocket")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldSocketResource {

    private Session session;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);


    @Inject
    public HelloWorldSocketResource() {

    }

    // called when the socket connection with the browser is established
    @OnWebSocketConnect
    public void handleConnect(Session session) {
        this.session = session;
    }

    // called when the connection closed
    @OnWebSocketClose
    public void handleClose(int statusCode, String reason) {
        System.out.println("Connection closed with statusCode="
                + statusCode + ", reason=" + reason);
    }

    // called when a message received from the browser
    @OnWebSocketMessage
    public void handleMessage(String message) {
        switch (message) {
            case "start":
                send("Current time service started");
                executor.scheduleAtFixedRate(() ->
                        send("Current time is: " + LocalTime.now()), 0, 5, TimeUnit.SECONDS);
                break;
            case "stop":
                send("Current time service stopped at: " + LocalTime.now());
                this.stop();
                break;
        }
    }

    // called in case of an error
    @OnWebSocketError
    public void handleError(Throwable error) {
        error.printStackTrace();
    }

    // sends message to browser
    private void send(String message) {
        try {
            if (session.isOpen()) {
                session.getRemote().sendString(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // closes the socket
    private void stop() {
        try {
            session.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
