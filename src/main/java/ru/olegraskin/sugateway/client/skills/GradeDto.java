package ru.olegraskin.sugateway.client.skills;

import lombok.Data;

@Data
public class GradeDto {

    private Long id;

    private String name;

    private String description;

    private int gradeProgress;

//    private Set<Skill> skills = new HashSet<>();
}
