package ru.avid.test.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "PRIORITY", catalog = "POSTGRES",schema = "TEST")
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column
    private String color;
    @OneToMany(mappedBy = "priority", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Collection<Task> tasks = new HashSet<>();
}
