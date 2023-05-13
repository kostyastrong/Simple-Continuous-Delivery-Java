package org.example.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
@Slf4j
public class Producer {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    Consumer consumer;

    @PostMapping("/print")
    public String sendMessage() {
        Integer id = requestRepository.getMaxId() + 1;
        Integer attempts = 0;
        do {
            try {
                ExecutorService executor = Executors.newFixedThreadPool(1);
                Future<Boolean> future = executor.submit(() -> consumer.print(id));
                future.get(5, TimeUnit.SECONDS);
                break;
            } catch (TimeoutException e) {
                log.error(String.format("fail, timeout, attempt num: %s", id));
            } catch (Exception e) {
                log.error(String.format("failed to send a request, attempt num: %s", id));
            }
            ++attempts;
        } while (attempts < 3);
        if (attempts >= 3) {
            return "message not printed";
        }

        return "message printed";
    }
}
