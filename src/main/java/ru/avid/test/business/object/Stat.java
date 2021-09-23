package ru.avid.test.business.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Stat {
    private Integer totalTask;
    private Integer totalCategoryTask;
    private Integer completedCategoryTask;
    private Integer completedTask;
    private Integer uncompletedCategoryTask;
    private Integer uncompletedTask;

}

