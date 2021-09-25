package ru.avid.test.business.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.avid.test.business.entity.Category;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class CategoryStat {
    private Category category;
    private Integer countTask;
}
