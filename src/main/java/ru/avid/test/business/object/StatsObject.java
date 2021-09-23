package ru.avid.test.business.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter  @Getter
public class StatsObject {
    private int totalTask;
    private int totalCategoryTask;
    private int completedTask;
    private int uncompletedTask;
}
