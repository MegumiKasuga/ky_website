package org.fairingstudio.kuayue_website.service;

import org.fairingstudio.kuayue_website.entity.ModFile;

import java.util.List;

public interface ModFileService {

    List<ModFile> getAllModFiles();

    List<ModFile> getModFilesByUserId(Integer userId);

    int saveModFile(ModFile modFile);
}
