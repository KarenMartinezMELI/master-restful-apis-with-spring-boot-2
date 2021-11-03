package com.stacksimplify.restservices.dtos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserDetailsWithId extends UserDetails{
    private Long id;
    private List<OrderDetailsWithId> orders;
}
