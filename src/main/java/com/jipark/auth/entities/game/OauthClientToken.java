package com.jipark.auth.entities.game;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="oauth_client_token")
@Getter
@Setter
public class OauthClientToken {
}
