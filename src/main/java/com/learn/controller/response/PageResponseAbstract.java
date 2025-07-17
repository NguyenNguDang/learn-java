package com.learn.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class PageResponseAbstract {
    public int pageNumber;
    public int pageSize;
    public long totalElements;
    public long totalPages;
}
