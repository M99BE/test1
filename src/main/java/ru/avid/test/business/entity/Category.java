package ru.avid.test.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.print.attribute.HashDocAttributeSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "CATEGORY", catalog = "POSTGRES", schema = "TEST")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @OneToMany(
            mappedBy = "category",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JsonIgnore
    private Collection<Task> tasks = new HashSet<>();
    public void addTask(Task task){
        tasks.add(task);
        task.setCategory(this);
    }
    public void removeTask(Task task){
        tasks.remove(task);
        task.setCategory(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return getId().equals(category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
