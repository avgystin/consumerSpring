package com;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final MessageRepository messageRepository;

    public KafkaConsumer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @KafkaListener(topics = "postedmessages", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);

        // Сохраняем в PostgreSQL
        MessageEntity entity = new MessageEntity();
        entity.setContent(message);
        messageRepository.save(entity);

        System.out.println("Message saved to database with id: " + entity.getId());
    }
}
