package org.fairingstudio.kuayue_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.fairingstudio.kuayue_website.entity.ModFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageObject {
    //赋初值防止在打开列表第一页时因没有参数报错
    //默认打开第一页
    private Long current = 1L;
    //默认每页显示5条记录
    private Long size = 5L;
    //总记录条数
    private Long total;
    //文件信息集合
    private List list;
    //是否有后页
    private boolean hasPrevious;
    //是否有前页
    private boolean hasNext;
    //总页数
    private Long pages;
    //上一页页数
    private Long previousPage;
    //下一页页数
    private Long nextPage;
    //前两页页数
    private Long secondPreviousPage;
    //后两页页数
    private Long secondNextPage;
    //是否有前两页
    private boolean hasSecondPreviousPage;
    //是否有后两页
    private boolean hasSecondNextPage;
}
