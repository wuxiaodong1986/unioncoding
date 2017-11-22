package com.unioncoding.service.file;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 阿里云oss文件处理
 * Created by 吴晓冬 on 2017/10/26.
 */
@Service
public class AliyunFileService implements FileService
{
    @Value("${aliyun.accessKeyId}")
    private  String accessKeyId;

    @Value("${aliyun.accessKeySecret}")
    private  String accessKeySecret;

    @Value("${aliyun.endpoint}")
    private  String endpoint;

    @Value("${aliyun.bucketName}")
    private  String bucketName;

    @Value("${aliyun.PRESIGN_URL_MAX_EXPIRATION_SECONDS}")
    private  Long PRESIGN_URL_MAX_EXPIRATION_SECONDS;

    public String save(MultipartFile file)
    {
        Date date = new Date();
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(date) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());
        try
        {
            ByteArrayInputStream io = new ByteArrayInputStream(file.getBytes());
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            Date expiration = new Date(new Date().getTime() + PRESIGN_URL_MAX_EXPIRATION_SECONDS) ;
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
            req.setExpiration(expiration);

            ossClient.putObject(bucketName, fileName,io);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            ossClient.shutdown();

            String url = signedUrl.toString();

            url =url.substring(0, url.indexOf(signedUrl.getQuery())-1);
            System.out.println(url);
            return url;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public  Integer delete(Integer id){
        String key = id.toString();
        try
        {
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            Date expiration = new Date(new Date().getTime() + PRESIGN_URL_MAX_EXPIRATION_SECONDS * 2000) ;
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
            req.setExpiration(expiration);
            ossClient.deleteObject(bucketName, key);

            ossClient.shutdown();
            return id;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public  byte[] get(Integer id){
        String key = id.toString();
        try
        {
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            Date expiration = new Date(new Date().getTime() + PRESIGN_URL_MAX_EXPIRATION_SECONDS * 2000) ;
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
            req.setExpiration(expiration);
            OSSObject object = ossClient.getObject(bucketName, key);
            // 获取ObjectMeta
            ObjectMetadata meta = object.getObjectMetadata();

            // 获取Object的输入流
            InputStream objectContent = object.getObjectContent();

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();

            int length = objectContent.available();

            byte[] data = new byte[length];

            int count = -1;

            while ((count = objectContent.read(data, 0, length)) != -1)

                outStream.write(data, 0, count);

            data = null;

            // 关闭流
            objectContent.close();

            ossClient.shutdown();

            return outStream.toByteArray();

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return new byte[]{};
    }
}
