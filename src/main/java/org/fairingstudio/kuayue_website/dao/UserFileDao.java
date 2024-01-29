package org.fairingstudio.kuayue_website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFileDao extends BaseMapper<UserFile> {

    //查询所有文件
    List<UserFile> selectAllFiles();

    //根据Id查询该用户的所有文件
    List<UserFile> selectFilesByUserId(Integer id);

    //保存文件信息
    void SaveFile(UserFile userFile);

    //根据Id查询对应文件
    UserFile selectFileById(Integer id);

    //更新
    void updateDownCounts(UserFile userFile);

    //根据Id删除记录
    void deleteFileById(Integer id);
}
