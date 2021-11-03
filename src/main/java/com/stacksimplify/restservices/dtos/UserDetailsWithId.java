package com.stacksimplify.restservices.dtos;
import lombok.*;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
//_embedded list
@Relation(collectionRelation = "users", itemRelation = "user")
public class UserDetailsWithId extends UserDetails{
    private Long id;
    private List<OrderDetailsWithId> orders;
}
