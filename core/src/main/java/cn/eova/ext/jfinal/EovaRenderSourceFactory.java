package cn.eova.ext.jfinal;

import com.jfinal.template.source.ClassPathSource;
import com.jfinal.template.source.FileSource;
import com.jfinal.template.source.ISource;
import com.jfinal.template.source.ISourceFactory;

/**
 *
 * EovaMeta 页面模版渲染组合策略
 * /eova 下资源渲染, 重置路径
 * @author Jieven
 */
public class EovaRenderSourceFactory implements ISourceFactory {

    public ISource getSource(String baseTemplatePath, String fileName, String encoding) {
        // System.out.println("EovaRenderSourceFactory:" + fileName);
        // Eova请求重置: /eova/x.html => classpath:/webapp/eova/x.html
        if (fileName.startsWith("/eova")) {
            /*
                自动追加根目录
                与Web容器配置保持 /eova/xxx 的访问逻辑
                undertow.resourcePath = classpath:webapp
             */
            fileName = "/webapp" + fileName;
            return new ClassPathSource(null, fileName, encoding);
        } else {
            // 其他请求再默认路径下访问
            return new FileSource(baseTemplatePath, fileName, encoding);
        }

    }

}