package org.example.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class Consumer {
    @Autowired
    private RequestRepository requestRepository;

    public Boolean print(Integer id) throws Exception {
        int error = new Random().nextInt() % 5;
        if (error == 1) {
            throw new Exception("");  // bug on the server side
        } else if (error == 0) {
            Thread.sleep(1000 * 60); // sleep for 60 minutes
        } else {
            Optional<RequestEntity> prevById = requestRepository.findById(id);
            if (prevById.isEmpty()) {
                log.warn(String.format("Request number %s", id));  // at least once
                requestRepository.save(new RequestEntity(id));
                // at most once
                // log.warn(String.format("Request number %s", id));
            }
        }
        return true;
    }
}
