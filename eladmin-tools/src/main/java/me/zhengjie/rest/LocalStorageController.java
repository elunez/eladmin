/*
 *  Copyright 2019-2025 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.rest;

import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.service.LocalStorageService;
import me.zhengjie.service.dto.LocalStorageDto;
import me.zhengjie.service.dto.LocalStorageQueryCriteria;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author Zheng Jie
* @date 2019-09-05
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "Tools: Local Storage Management")
@RequestMapping("/api/localStorage")
public class LocalStorageController {

    private final LocalStorageService localStorageService;

    @GetMapping
    @ApiOperation("Query files")
    @PreAuthorize("@el.check('storage:list')")
    public ResponseEntity<PageResult<LocalStorageDto>> queryFile(LocalStorageQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(localStorageService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation("Export data")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('storage:list')")
    public void exportFile(HttpServletResponse response, LocalStorageQueryCriteria criteria) throws IOException {
        localStorageService.download(localStorageService.queryAll(criteria), response);
    }

    @PostMapping
    @ApiOperation("Upload file")
    @PreAuthorize("@el.check('storage:add')")
    public ResponseEntity<Object> createFile(@RequestParam String name, @RequestParam("file") MultipartFile file){
        localStorageService.create(name, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Upload image")
    @PostMapping("/pictures")
    public ResponseEntity<LocalStorage> uploadPicture(@RequestParam MultipartFile file){
        // Determine whether the file is an image
        String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
        if(!FileUtil.IMAGE.equals(FileUtil.getFileType(suffix))){
            throw new BadRequestException("Only images can be uploaded");
        }
        LocalStorage localStorage = localStorageService.create(null, file);
        return new ResponseEntity<>(localStorage, HttpStatus.OK);
    }

    @PutMapping
    @Log("Update file")
    @ApiOperation("Update file")
    @PreAuthorize("@el.check('storage:edit')")
    public ResponseEntity<Object> updateFile(@Validated @RequestBody LocalStorage resources){
        localStorageService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("Delete file")
    @DeleteMapping
    @ApiOperation("Batch delete")
    public ResponseEntity<Object> deleteFile(@RequestBody Long[] ids) {
        localStorageService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}