package org.fairingstudio.kuayue_website.service.impl;

import org.fairingstudio.kuayue_website.dao.ModFileDao;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Override
    @Transactional
    public int saveModFile(ModFile modFile) {

        modFile.setDownloadCounts(0);
        modFile.setLikesCounts(0);
        modFile.setUploadTime(new Date());

        int num = modFileDao.insertModFile(modFile);
        return num;
    }

    @Override
    public ModFile getModById(Integer id) {

        ModFile modFile = modFileDao.selectModById(id);
        return modFile;
    }

    @Override
    @Transactional
    public int updateModFile(ModFile modFile) {

        int num = modFileDao.updateModFile(modFile);
        return num;
    }
}
