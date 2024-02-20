package com.epam.backendservicesmentoring.client;

import com.epam.backendservicesmentoring.stubs.pingpong.PingPongRequest;
import com.epam.backendservicesmentoring.stubs.pingpong.PingPongResponse;
import com.epam.backendservicesmentoring.stubs.pingpong.PingPongServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PingPongClient {
    private static final Logger LOGGER = LogManager.getLogger(PingPongClient.class);

    public static void main(String[] args) {
        ManagedChannel channel =
                ManagedChannelBuilder.forTarget("localhost:8085").usePlaintext().build();

        PingPongServiceGrpc.PingPongServiceBlockingStub stub
                = PingPongServiceGrpc.newBlockingStub(channel);

        PingPongResponse response = stub.getMessage(PingPongRequest.newBuilder()
                .setMessage("Ping")
                .build());
        LOGGER.info("Client {}", response.getMessage());
    }
}
