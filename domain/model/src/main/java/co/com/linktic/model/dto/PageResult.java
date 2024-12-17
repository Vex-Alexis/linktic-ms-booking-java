package co.com.linktic.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;



}
