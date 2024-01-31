package org.fairingstudio.kuayue_website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.fairingstudio.kuayue_website.dao.ModFileDao;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.ModParamInput;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.entity.PageObject;
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

    @Override
    @Transactional
    public int deleteModFile(Integer id) {

        int num = modFileDao.deleteModById(id);
        return num;
    }

    @Override
    public PageObject getModFilesPage(PageObject pageObject, ModParamInput modParamInput) {
        //进行分页
        //使用mybatis-plus的分页组件
        if (pageObject.getCurrent() == null) {pageObject.setCurrent(1L);}   //当查询页码为空时，设置为查询第一页。

        Page<ModFile> page = new Page<>(pageObject.getCurrent(), pageObject.getSize());
        String modMCVersion = null;
        String env = null;
        //查询条件
        if (!"".equals(modParamInput.getMCVersion()) && modParamInput.getMCVersion() != null) {
            modMCVersion = modParamInput.getMCVersion();    //当MC版本不为空且不为空字符串时赋值查询参数
        }
        if (!"".equals(modParamInput.getEnv()) && modParamInput.getEnv() != null) {
            env = modParamInput.getEnv();   //当运行环境不为空且不为空字符串时赋值查询参数
        }
        Page<ModFile> modFilePage = modFileDao.selectModFilePage(page, modMCVersion, env);

        PageObject result = new PageObject();
        long current = modFilePage.getCurrent();
        result.setCurrent(current); //当前页码
        result.setSize(modFilePage.getSize());  //每页展示记录条数
        result.setTotal(modFilePage.getTotal());    //总记录条数
        result.setList(modFilePage.getRecords());   //文件信息
        result.setHasPrevious(modFilePage.hasPrevious());   //是否有上一页
        result.setHasNext(modFilePage.hasNext());   //是否有下一页

        result.setPages(modFilePage.getPages());    //总页数
        result.setPreviousPage(current - 1L);       //上一页页码
        result.setNextPage(current + 1L);           //下一页页码
        result.setSecondPreviousPage(current - 2L); //上两页页码
        result.setSecondNextPage(current + 2L);     //下两页页码
        result.setHasSecondPreviousPage(current - 2L >= 1L);    //是否有前两页
        result.setHasSecondNextPage(current + 2L <= modFilePage.getPages());    //是否有后两页
        //System.out.println("result = " + result);

        return result;
    }
}
