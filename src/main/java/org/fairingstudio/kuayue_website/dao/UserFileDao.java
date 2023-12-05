package org.fairingstudio.kuayue_website.dao;

import org.fairingstudio.kuayue_website.entity.UserFile;

import java.util.List;

public interface UserFileDao {

    //查询所有文件
    List<UserFile> getAllFiles();

    //根据Id查询该用户的所有文件
    List<UserFile> getFilesByUserId(Integer id);

    //保存文件信息
    void SaveFile(UserFile userFile);

    //根据Id查询对应文件
    UserFile getFilesById(Integer id);

    //更新
    void update(UserFile userFile);

    //根据Id删除记录
    void delete(Integer id);
}
