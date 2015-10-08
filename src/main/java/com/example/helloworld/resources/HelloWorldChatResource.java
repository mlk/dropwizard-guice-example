package com.example.helloworld.resources;

import com.example.helloworld.core.ChatMessage;
import com.example.helloworld.services.ChatMessageEncoderDecoder;
import com.example.helloworld.services.CounterService;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Message;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@ManagedService(path = "/chat")
public final class HelloWorldChatResource {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessage.class);

    private final CounterService counterService;

    @Inject
    public HelloWorldChatResource(CounterService counterService) {
        this.counterService = counterService;
    }

    /**
     * Invoked when the connection as been fully established and suspended, e.g ready for receiving messages.
     *
     * @param r the atmosphere resource
     */
    @Ready
    public final void onReady(final AtmosphereResource r){
        logger.info("Browser {} connected.", r.uuid());
    }

    /**
     * Invoked when the client disconnect or when an unexpected closing of the underlying connection happens.
     *
     * @param event the event
     */
    @Disconnect
    public final void onDisconnect(final AtmosphereResourceEvent event){
        if(event.isCancelled())
            logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
        else if(event.isClosedByClient())
            logger.info("Browser {} closed the connection", event.getResource().uuid());
    }

    /**
     * Simple annotated class that demonstrate how
     * {@link org.atmosphere.config.managed.Encoder} and {@link org.atmosphere.config.managed.Decoder
     * can be used.
     *
     * @param message an instance of {@link ChatMessage }
     * @return the chat message
     * @throws IOException
     */
    @Message(encoders = {ChatMessageEncoderDecoder.class}, decoders = {ChatMessageEncoderDecoder.class})
    public final ChatMessage onMessage(final ChatMessage message) {
        logger.info("{} just send {}", message.getOriginatingSystem(), message.getMessagePayload());
        message.setMessagePayload(counterService.next() + " - " + message.getMessagePayload());
        return message;
    }

}