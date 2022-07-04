package de.lukas.imagenetwork.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "friends")
@NoArgsConstructor
public class Friend {
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(name = "userid")
    private Long userId;
    @Column(name = "friendid")
    private Long friendId;
}
