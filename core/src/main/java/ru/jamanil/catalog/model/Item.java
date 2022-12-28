package ru.jamanil.catalog.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;

/**
 * @author Victor Datsenko
 * 15.12.2022
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Item {
    @Id
    private String id;
    private String category;
    private String name;
    private Float price;
    private Float discount;
    private Map<String, String> characteristics;
    private String description;
    private List<Review> reviews;
}
