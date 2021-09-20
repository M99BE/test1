package ru.avid.test.business.search;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class SearchTask {
    private String category = "";
    private Boolean completed = false;
    private String priority = "";

    @JsonSetter("category")
    public void setCategory(String category) {
        if (category != null)
            this.category = category;
    }
    @JsonSetter("completed")
    public void setCompleted(Boolean completed) {
        if (completed != null)
            this.completed = completed;
    }
    @JsonSetter("priority")
    public void setPriority(String priority) {
        if(priority != null)
            this.priority = priority;
    }
}
