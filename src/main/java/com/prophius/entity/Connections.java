package com.prophius.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "connections")
@Data
public class Connections extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long connection_id;

    private long followerId;
    private long followeeId;




}
