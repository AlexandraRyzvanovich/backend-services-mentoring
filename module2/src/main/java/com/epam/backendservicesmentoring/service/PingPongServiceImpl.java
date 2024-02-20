package com.epam.backendservicesmentoring.service;

import com.epam.backendservicesmentoring.stubs.pingpong.PingPongRequest;
import com.epam.backendservicesmentoring.stubs.pingpong.PingPongResponse;
import com.epam.backendservicesmentoring.stubs.pingpong.PingPongServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {
    private static final Logger LOGGER = LogManager.getLogger(PingPongServiceImpl.class);
    private static final String KEY_MESSAGE = "Pong";
    private static final String DEFAULT_ERROR_MESSAGE = "Wrong";


    @Override
    public void getMessage(PingPongRequest request, StreamObserver<PingPongResponse> responseObserver) {
        PingPongResponse.Builder pingPongMessageBuilder;
        if (checkMessage(request.getMessage())) {
            pingPongMessageBuilder = PingPongResponse.newBuilder()
                    .setMessage(KEY_MESSAGE)
                    .setDateNow(LocalDateTime.now().toString());
            LOGGER.debug("Response has been build. Message is correct");
        } else {
            pingPongMessageBuilder = PingPongResponse.newBuilder()
                    .setMessage(DEFAULT_ERROR_MESSAGE)
                    .setDateNow(LocalDateTime.now().toString());
            LOGGER.debug("Response has been build. Message {} is NOT correct", request.getMessage());
        }

        PingPongResponse pingPongResponse = pingPongMessageBuilder.build();

        responseObserver.onNext(pingPongResponse);
        responseObserver.onCompleted();
    }

    private boolean checkMessage(String message) {
        return message.equals("Ping");
    }
}
