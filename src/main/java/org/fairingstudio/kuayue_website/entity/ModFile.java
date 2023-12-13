package org.fairingstudio.kuayue_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModFile {

    private Integer id;

    private String modFileName;

    private String MCVersion;

    private String fileSize;

    private Integer downloadCounts;

    private Integer likesCounts;

    private Date uploadTime;

    private Date updateTime;

    private Integer userId;
}
