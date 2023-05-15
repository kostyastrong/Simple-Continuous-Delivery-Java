package org.example.outbox;

import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "outbox_users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer broker = 0;
    private String userName;
    private String password;  // without hashing
    private Integer request_number;
    public UserEntity(String json) {
        UserEntity user = new Gson().fromJson(json, UserEntity.class);
        this.password = user.password;
        this.userName = user.userName;
        this.broker = user.broker;
        this.request_number = user.request_number;
    }

    public Long getId() {
        return id;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
