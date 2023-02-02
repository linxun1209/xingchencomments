package com.xingchen.comments.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xing'chen
 */
@Data
public class ScrollResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
