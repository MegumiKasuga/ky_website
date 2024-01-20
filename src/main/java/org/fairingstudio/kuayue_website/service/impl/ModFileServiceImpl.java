package org.fairingstudio.kuayue_website.service.impl;

import org.fairingstudio.kuayue_website.dao.ModFileDao;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModFileServiceImpl implements ModFileService {

    @Resource
    private ModFileDao modFileDao;

    @Override
    public List<ModFile> getAllModFiles() {
        List<ModFile> modFiles = modFileDao.selectAllModFiles();
        return modFiles;
    }

    @Override
    public List<ModFile> getModFilesByUserId(Integer userId) {

        List<ModFile> modFiles = modFileDao.selectModFilesByUserId(userId);
        return modFiles;
    }
}
