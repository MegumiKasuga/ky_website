package org.fairingstudio.kuayue_website.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("ky_mod_file")
public class ModFile {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("mod_file_name")
    private String modFileName;

    @TableField("mc_version")
    private String MCVersion;

    @TableField("file_size")
    private String fileSize;

    @TableField("download_counts")
    private Integer downloadCounts;

    @TableField("likes_counts")
    private Integer likesCounts;

    @TableField("upload_time")
    private Date uploadTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("user_id")
    private Integer userId;

    private String path;
}
