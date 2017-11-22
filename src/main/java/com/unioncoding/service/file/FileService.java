package com.unioncoding.service.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 吴晓冬 on 2017/11/22.
 */
public interface FileService
{
    public String save(MultipartFile file);
}
