package com.jet.restaurants.service.openclose.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @Getter
    private String name;

    @Column
    @Getter
    @Setter
    @Enumerated
    private Status status;

    public static Restaurant create(String name, Status status) {
        val restaurant = builder()
                .name(name)
                .status(status)
                .build();

        return restaurant;
    }

}
