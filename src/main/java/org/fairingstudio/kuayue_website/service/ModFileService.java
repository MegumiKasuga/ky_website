package org.fairingstudio.kuayue_website.service;

import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.ModParamInput;
import org.fairingstudio.kuayue_website.entity.PageObject;

import java.util.List;

public interface ModFileService {

    List<ModFile> getAllModFiles();

    List<ModFile> getModFilesByUserId(Integer userId);

    int saveModFile(ModFile modFile);

    ModFile getModById(Integer id);

    int updateModFile(ModFile modFile);

    int deleteModFile(Integer id);

    PageObject getModFilesPage(PageObject pageObject, ModParamInput modParamInput);
}
