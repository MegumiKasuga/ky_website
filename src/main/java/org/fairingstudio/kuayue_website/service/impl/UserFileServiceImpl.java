package org.fairingstudio.kuayue_website.service.impl;

import org.fairingstudio.kuayue_website.dao.UserFileDao;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserFileServiceImpl implements UserFileService {

    @Resource
    private UserFileDao userFileDao;

    @Override
    public List<UserFile> getAllFiles() {

        List<UserFile> userFiles = userFileDao.selectAllFiles();
        return userFiles;
    }
}
