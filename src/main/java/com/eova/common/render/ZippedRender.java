/**
 * Copyright (c) 2013-2017, Sagittaria(shuyang_x@hotmail.com). No rights reserved.
 * <p/>
 * Licensed under the GPL license: http://www.gnu.org/licenses/gpl.txt
 * To use it on other terms please contact us at 1623736450@qq.com
 */
package com.eova.common.render;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.eova.common.utils.io.ZipUtil;
import com.jfinal.render.Render;

public class ZippedRender extends Render{
    private String targetDirectoryPath;
    private String filename;
    
    /**
     * 文件夹打包下载
     * 
     * @param targetDirectoryPath 目标文件夹
     * @param filename 下载文件名
     */
    public ZippedRender(String targetDirectoryPath, String filename){
        this.targetDirectoryPath = targetDirectoryPath;
        this.filename = filename;
    }
    
    @Override
    public void render() {
        String dest = targetDirectoryPath + File.separator + filename + ".zip";
        ZipUtil.zip(targetDirectoryPath, dest, null);//全部打包到单个zip中，放在原来的目录下面

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "close");  
        response.setHeader("Content-Type", "application/octet-stream");
        
        OutputStream ops = null;  
        FileInputStream fis =null;
        
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filename, "UTF-8") + ".zip");
            
            byte[] buffer = new byte[8192];  
            int bytesRead = 0;  
            
            ops = response.getOutputStream();  
            fis = new FileInputStream(dest);  
            
            while((bytesRead = fis.read(buffer, 0, 8192)) != -1){  
                 ops.write(buffer, 0, bytesRead);  
            }  
            ops.flush();
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } // 应在finally里关流，确保close方法被执行到
            if (ops != null){
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(!new File(dest).delete()){
                System.err.println("warning: failed to remove temp file [" + dest + "]");
            }
        }
    }
    
}
