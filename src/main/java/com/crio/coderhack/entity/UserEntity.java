package com.crio.coderhack.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Document(collection = "users")
@NoArgsConstructor
public class UserEntity {

    private String username;

    private Integer score;


    
    private Set<String> badge;

    @Id
    private Long id;
}
