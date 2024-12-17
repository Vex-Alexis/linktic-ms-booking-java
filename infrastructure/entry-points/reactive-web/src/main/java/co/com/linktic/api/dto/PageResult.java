package co.com.linktic.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    public PageResult(List<T> content, int currentPage, int pageSize, long totalElements, int totalPages) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

}
