package ru.avid.test.business.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchValues {
    private String title;
    private Long categoryId;
    private Integer completed;
    private Date dateFrom;
    private Date fromTo;

    private Integer pageNumber;
    private Integer pageSize;

    private String sortColumn;
    private String sortDirection;
}
