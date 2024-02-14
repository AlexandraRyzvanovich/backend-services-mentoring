package com.epam.backendservicesmentoring.server;

import com.epam.backendservicesmentoring.service.PingPongServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PingPongServer {
    private static final Logger LOGGER = LogManager.getLogger(PingPongServer.class);
    private static final int PORT = 8089;
    private Server server;

    public void startServer() {
        try {
            server = ServerBuilder.forPort(PORT)
                    .addService(new PingPongServiceImpl())
                    .build()
                    .start();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                LOGGER.debug("Clean shutdown");
                PingPongServer.this.stopServer();
            }));

        } catch (IOException e) {
            LOGGER.error("Server could not start. Error: {}", e.getMessage());
        }
    }

    public void stopServer() {
        if (server != null) {
            try {
                server.shutdown().awaitTermination(30, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LOGGER.error("Server could not stop.  Error: {}", e.getMessage());
            }
        }
    }

    public void blockUntilShutdown() {
        if (server != null) {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                LOGGER.error("Server could not be blocked for shutdown.  Error: {}", e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        PingPongServer pingPongServer = new PingPongServer();
        pingPongServer.startServer();
        pingPongServer.blockUntilShutdown();
    }

}
