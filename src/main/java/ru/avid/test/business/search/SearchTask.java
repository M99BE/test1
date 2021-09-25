package ru.avid.test.business.search;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class SearchTask extends SearchBase{
    private Boolean completed;
    private Long priorityId ;
    private String categoryTitle = "";

    @JsonSetter("categoryTitle")
    public void setCategoryTitle(String categoryTitle) {
        if (categoryTitle != null)
            this.categoryTitle = categoryTitle;
    }
}
