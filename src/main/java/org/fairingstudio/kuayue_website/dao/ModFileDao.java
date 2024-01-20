package org.fairingstudio.kuayue_website.dao;

import org.fairingstudio.kuayue_website.entity.ModFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModFileDao {

    List<ModFile> selectAllModFiles();

    List<ModFile> selectModFilesByUserId(Integer userId);

}
