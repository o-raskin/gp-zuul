package ru.olegraskin.sugateway.dto.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GradeDto {

    private Long id;

    private String name;

    private String description;

    private int gradeProgress;

//    private Set<Skill> skills = new HashSet<>();
}
