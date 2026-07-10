package com.example.headhanter.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Category {

    private String name;
    private Integer parentId;
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
}
