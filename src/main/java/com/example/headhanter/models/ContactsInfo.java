package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactsInfo {
    private Integer typeId;
    private Integer resumeId;
    private String values;
    private Integer id;

    public ContactsInfo(Integer typeId) {
        this.typeId = typeId;
    }
}
