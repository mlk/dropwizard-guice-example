package com.example.helloworld.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

        private String messagePayload;
        private String originatingSystem;
        private long time;

        public ChatMessage(String originatingSystem, String messagePayload){
            this.originatingSystem = originatingSystem;
            this.messagePayload = messagePayload;
            this.time = new Date().getTime();
        }

}
