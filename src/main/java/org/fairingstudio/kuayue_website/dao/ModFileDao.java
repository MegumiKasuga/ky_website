package org.fairingstudio.kuayue_website.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.geometry.Point3D;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModFileDao extends BaseMapper<ModFile> {

    List<ModFile> selectAllModFiles();

    List<ModFile> selectModFilesByUserId(Integer userId);

    int insertModFile(ModFile modFile);

    ModFile selectModById(Integer id);

    int updateModFile(ModFile modFile);

    int deleteModById(Integer id);

    Page<ModFile> selectModFilePage(Page<ModFile> page, String MCVersion, String env);
}
