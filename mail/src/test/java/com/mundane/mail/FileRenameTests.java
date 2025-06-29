package com.mundane.mail;

import cn.hutool.core.io.FileUtil;
import com.mundane.mail.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
@Slf4j
public class FileRenameTests {


    @Test
    public void testRename() throws Exception {
        String dirPath = "D:\\BaiduYunDownload\\coderwhy\\12-深入TypeScript语法+工具封装实战\\day103_模块化-类型声明-请求库封装-内置工具-类型体";
        List<String> list = FileUtil.listFileNames(dirPath);
        for (String name : list) {
            String newName = name.replace("[海量一手itdjs.com]", "");
//            FileUtil.rename(new File(dirPath, name), );
            log.info("newName = {}", newName);
            FileUtil.rename(new File(dirPath, name), newName, true);
        }

    }
}
