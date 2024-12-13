package com.adam9e96.ThymeleafStudy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // getter , setter
@AllArgsConstructor
public class Member {
    private Integer id;
    private String name;
}
