package D6B.D_discover_alarm.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Alarm")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "receiver_id")
    private String receiverId;
    @Column(name = "field")
    private Long Field;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "read_or_not")
    private boolean readOrNot;
    @Column(name = "type")
    private Integer type;

}
