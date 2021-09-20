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
    private String title = "";
    @JsonSetter("title")
    public void setTitle(String title) {
        if (title != null)
            this.title = title;
    }
}