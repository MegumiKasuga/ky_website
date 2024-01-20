package org.fairingstudio.kuayue_website.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("ky_files")
public class UserFile {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String oldFileName;

    private String newFileName;

    private String ext;

    private String path;

    private String size;

    private String type;

    private String isImg;

    private int downCounts;

    private Date uploadTime;

    private int userId;

}
