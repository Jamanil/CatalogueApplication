package ru.jamanil.catalog.model;

import lombok.*;
import java.util.Date;

/**
 * @author Victor Datsenko
 * 15.12.2022
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Review {
    private String customer;
    private Short rating;
    private Date date;
    private String comment;
}
