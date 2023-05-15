package org.example.outbox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Scheduled(fixedRate = 5000)
    public void scheduler() {
        log.info("scheduler working");
        for (UserEntity user : userRepository.findByBroker(0)) {
            rabbitMqSender.send(user);
            user.setBroker(1);
            userRepository.save(user);
            log.info(String.format("User num. %s was sent to kafka", user.getId()));
        }
    }


    public UserEntity newUser(String name, String password) {
        UserEntity dbUser = UserEntity.builder().userName(name).password(password).broker(0).build();
        userRepository.save(dbUser);
        rabbitMqSender.send(dbUser);
        dbUser.setBroker(1);
        userRepository.save(dbUser);
        log.info(String.format("number of users: %s", userRepository.findAll().size()));
        return dbUser;
    }
}
