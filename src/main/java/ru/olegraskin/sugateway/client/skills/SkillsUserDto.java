package ru.olegraskin.sugateway.client.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillsUserDto {

    private Long id;

    private GradeDto grade;
}
