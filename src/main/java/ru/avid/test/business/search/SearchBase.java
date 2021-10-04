package ru.avid.test.business.search;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class SearchBase {
    private Integer pageNumber;
    private Integer pageSize;

    private String sortColumn;
    private String sortDirection;

    private String title = "";
    private long userId = 0L;

    private Integer pageNumber;
    private Integer pageSize;

    private String sortColumn;
    private String sortDirection;

    @JsonSetter("title")
    public void setTitle(String title) {
        if (title != null)
            this.title = title;
    }

}
