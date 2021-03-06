package com.byteblogs.common.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byteblogs.common.cache.ConfigCache;
import com.byteblogs.common.constant.Constants;
import com.byteblogs.helloblog.config.dao.ConfigDao;
import com.byteblogs.helloblog.config.domain.po.Config;
import com.byteblogs.system.init.InitFileConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private ConfigDao configDao;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        String defaultPath;
        try {
            Config config = configDao.selectOne(new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, Constants.DEFAULT_PATH));
            defaultPath = config.getConfigValue();
        } catch (Exception e) {
            if (InitFileConfig.isWindows()){
                defaultPath = "D:/helloblog/blog/";
            } else {
                defaultPath = "/home/helloblog/blog/";
            }
        }

        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + defaultPath);
    }
}