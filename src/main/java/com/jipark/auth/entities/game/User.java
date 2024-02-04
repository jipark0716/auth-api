package com.jipark.auth.entities.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    private long id;

    @Column("name")
    private final String name;

    @Column("updated_at")
    @ReadOnlyProperty
    private Timestamp updatedAt;

    @Column("created_at")
    @ReadOnlyProperty
    private Timestamp createdAt;

    @Transient
    private List<OauthClientToken> tokens;
}
