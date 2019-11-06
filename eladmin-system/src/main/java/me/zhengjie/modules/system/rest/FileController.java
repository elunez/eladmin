package me.zhengjie.modules.system.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.config.DataScope;
import me.zhengjie.modules.monitor.service.RedisService;
import me.zhengjie.modules.security.security.JwtUser;
import me.zhengjie.modules.system.domain.FileModel;
import me.zhengjie.modules.system.domain.FileSort;
import me.zhengjie.modules.system.repository.FileRepository;
import me.zhengjie.modules.system.service.FileService;
import me.zhengjie.modules.system.service.FileSortService;
import me.zhengjie.modules.system.service.dto.FileQueryCriteria;
import me.zhengjie.modules.system.service.mapper.FileMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@RestController
@RequestMapping("api")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileSortService fileSortService;

//    @Autowired
//    private FileMapper fileMapper;

    @Autowired
    private DataScope dataScope;

    @Autowired
    private RedisService redisService;

    @Autowired
    private FileRepository fileRepository;

    @Value("${filePath}")
    private String UPLOAD_FOLDER;

    private static final String ENTITY_NAME = "file";

    @Log("查询目录")
    @GetMapping(value = "/file")
    @PreAuthorize("hasAnyRole('ADMIN','FILE_ALL','FILE_SELECT')")
    public ResponseEntity getFiles(FileQueryCriteria criteria, Pageable pageable){
        Set<String> fileSortSet = new HashSet<>();
        Set<String> result = new HashSet<>();
        if (!ObjectUtils.isEmpty(criteria.getFileSortId())) {
            fileSortSet.add(criteria.getFileSortId());
        }
        result.addAll(fileSortSet);
        criteria.setFileSortIds(result);
//        Page<FileModel> page = fileRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
//        Map m  = PageUtil.toPage(page.map(fileMapper::toDto));
        Object m2 = (Map) fileService.queryAll(criteria,pageable);
        return new ResponseEntity(m2 ,HttpStatus.OK);
    }

    @Log("新增文件")
    @PostMapping(value = "/file")
    @PreAuthorize("hasAnyRole('ADMIN','FILE_ALL','FILE_CREATE')")
    public ResponseEntity create(HttpServletRequest request){
//        if (Objects.isNull(files)) {
//            return new ResponseEntity("文件为空，请重新上传",HttpStatus.CREATED);
//        }
        JwtUser user =(JwtUser)redisService.getObjectByKey("user");
        String userName="";
        if(null!=user){
            userName=user.getUsername();
        }
        String fileSortId = request.getParameter("fileSortId");
        FileSort sort = new FileSort();
        sort.setId(fileSortId);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        Path path = Paths.get(UPLOAD_FOLDER);
        try {
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            if(multipartResolver.isMultipart(request)){
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multipartHttpServletRequest.getFileNames();
                while (iter.hasNext()){
                    FileModel fileModel = new FileModel();
                    MultipartFile file = multipartHttpServletRequest.getFile(iter.next());
                    String originFileName = file.getOriginalFilename();
                    String extensinName = FileUtil.getExtensionName(file.getOriginalFilename());
                    String newFileName = StringUtils.createID()+"."+extensinName;
                    file.transferTo(new File(UPLOAD_FOLDER+"\\"+newFileName));
                    fileModel.setUploader(userName);
                    fileModel.setFileName(newFileName);
                    fileModel.setOriginName(originFileName);
                    fileModel.setFileSort(sort);
                    fileModel.setFileType(extensinName);
                    fileService.create(fileModel);
                }

            }
            return new ResponseEntity("文件上传成功",HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("文件上传失败",HttpStatus.CREATED);

        }


    }

    @Log("修改文件")
    @PutMapping(value = "/file")
    @PreAuthorize("hasAnyRole('ADMIN','FILE_ALL','FILE_EDIT')")
    public ResponseEntity update(@Validated(FileModel.Update.class) @RequestBody FileModel resources){
        fileService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/file/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','FILE_ALL','FILE_DELETE')")
    public ResponseEntity delete(@PathVariable String id){
        fileService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    /**
     * 	文件下载到客户端
     *
     * @param
     * @return
     */
    @Log("文件下载")
    @GetMapping(value = "/fileDownload")
    @PreAuthorize("hasAnyRole('ADMIN','FILE_ALL','FILE_SELECT')")
    protected ResponseEntity<org.springframework.core.io.Resource> download(String fileNames) {
        String[] fileArray = fileNames.split(",");
        File file = new File(UPLOAD_FOLDER+"\\"+fileArray[0]);
        String fileName = fileArray[0];
        org.springframework.core.io.Resource body = new FileSystemResource(file);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String header = request.getHeader("User-Agent").toUpperCase();
        HttpStatus status = HttpStatus.CREATED;
        try {
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                // IE下载文件名空格变+号问题
                fileName = URLEncoder.encode(fileName, "UTF-8");
                fileName = fileName.replace("+", "%20");
                status = HttpStatus.OK;
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        //headers.setContentDisposition();
        headers.setContentLength(file.length());
        return new ResponseEntity<org.springframework.core.io.Resource>(body, headers, status);
    }

//    @Log("文件下载")
//    @GetMapping(value = "/fileDownload")
//    @PreAuthorize("hasAnyRole('ADMIN','FILE_ALL','FILE_SELECT')")
//    public void download(String fileNames, HttpServletResponse response) {
//        try {
//            String[] fileArray = fileNames.split(",");
//            File file = new File(UPLOAD_FOLDER+"\\"+fileArray[0]);
//            String fileName = fileArray[0];
//            if(StringUtils.isNotBlank(fileName)){
//            // 取得文件名。
//            // 以流的形式下载文件。
//            InputStream fis = new BufferedInputStream(new FileInputStream(file));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//            // 清空response
//            response.reset();
//            String uncod= URLDecoder.decode(fileName,"UTF-8");
//            fileName = new String(uncod.getBytes("UTF-8"), "iso-8859-1");
//            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(fileName)));
//            // 设置response的Header
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
//        }
//// path是指欲下载的文件的路径。
//        } catch (IOException ex) {
// ex.printStackTrace();
//}
//}

}