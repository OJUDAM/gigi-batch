package com.ujo.gigiScheduler.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseEntity {
    private String createdAt;
    protected String updatedAt;
}
