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
package me.zhengjie.modules.maint.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.maint.domain.Deploy;
import me.zhengjie.modules.maint.domain.DeployHistory;
import me.zhengjie.modules.maint.service.DeployService;
import me.zhengjie.modules.maint.service.dto.DeployDto;
import me.zhengjie.modules.maint.service.dto.DeployQueryCriteria;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Slf4j
@RestController
@Api(tags = "运维：部署管理")
@RequiredArgsConstructor
@RequestMapping("/api/deploy")
public class DeployController {

	private final String fileSavePath = FileUtil.getTmpDirPath()+"/";
    private final DeployService deployService;


	@ApiOperation("导出部署数据")
	@GetMapping(value = "/download")
	@PreAuthorize("@el.check('database:list')")
	public void exportDeployData(HttpServletResponse response, DeployQueryCriteria criteria) throws IOException {
		deployService.download(deployService.queryAll(criteria), response);
	}

    @ApiOperation(value = "查询部署")
    @GetMapping
	@PreAuthorize("@el.check('deploy:list')")
    public ResponseEntity<PageResult<DeployDto>> queryDeployData(DeployQueryCriteria criteria, Pageable pageable){
		return new ResponseEntity<>(deployService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增部署")
    @ApiOperation(value = "新增部署")
    @PostMapping
	@PreAuthorize("@el.check('deploy:add')")
    public ResponseEntity<Object> createDeploy(@Validated @RequestBody Deploy resources){
		deployService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改部署")
    @ApiOperation(value = "修改部署")
    @PutMapping
	@PreAuthorize("@el.check('deploy:edit')")
    public ResponseEntity<Object> updateDeploy(@Validated @RequestBody Deploy resources){
        deployService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

	@Log("删除部署")
	@ApiOperation(value = "删除部署")
	@DeleteMapping
	@PreAuthorize("@el.check('deploy:del')")
	public ResponseEntity<Object> deleteDeploy(@RequestBody Set<Long> ids){
		deployService.delete(ids);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Log("上传文件部署")
	@ApiOperation(value = "上传文件部署")
	@PostMapping(value = "/upload")
	@PreAuthorize("@el.check('deploy:edit')")
	public ResponseEntity<Object> uploadDeploy(@RequestBody MultipartFile file, HttpServletRequest request)throws Exception{
		Long id = Long.valueOf(request.getParameter("id"));
		String fileName = "";
		if(file != null){
			fileName = FileUtil.verifyFilename(file.getOriginalFilename());
			File deployFile = new File(fileSavePath + fileName);
			FileUtil.del(deployFile);
			file.transferTo(deployFile);
			//文件下一步要根据文件名字来
			deployService.deploy(fileSavePath + fileName ,id);
		}else{
			log.warn("没有找到相对应的文件");
		}
		Map<String,Object> map = new HashMap<>(2);
		map.put("error",0);
		map.put("id",fileName);
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	@Log("系统还原")
	@ApiOperation(value = "系统还原")
	@PostMapping(value = "/serverReduction")
	@PreAuthorize("@el.check('deploy:edit')")
	public ResponseEntity<Object> serverReduction(@Validated @RequestBody DeployHistory resources){
		String result = deployService.serverReduction(resources);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

	@Log("服务运行状态")
	@ApiOperation(value = "服务运行状态")
	@PostMapping(value = "/serverStatus")
	@PreAuthorize("@el.check('deploy:edit')")
	public ResponseEntity<Object> serverStatus(@Validated @RequestBody Deploy resources){
		String result = deployService.serverStatus(resources);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

	@Log("启动服务")
	@ApiOperation(value = "启动服务")
	@PostMapping(value = "/startServer")
	@PreAuthorize("@el.check('deploy:edit')")
	public ResponseEntity<Object> startServer(@Validated @RequestBody Deploy resources){
		String result = deployService.startServer(resources);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}

	@Log("停止服务")
	@ApiOperation(value = "停止服务")
	@PostMapping(value = "/stopServer")
	@PreAuthorize("@el.check('deploy:edit')")
	public ResponseEntity<Object> stopServer(@Validated @RequestBody Deploy resources){
		String result = deployService.stopServer(resources);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
}
