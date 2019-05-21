package ${package}.rest;

import me.zhengjie.aop.log.Log;
import ${package}.domain.${className};
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}DTO;
import ${package}.service.query.${className}QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("api")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${changeClassName}Service;

    @Autowired
    private ${className}QueryService ${changeClassName}QueryService;

    @Log("查询${className}")
    @GetMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public ResponseEntity get${className}s(${className}DTO resources, Pageable pageable){
        return new ResponseEntity(${changeClassName}QueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增${className}")
    @PostMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ${className} resources){
        return new ResponseEntity(${changeClassName}Service.create(resources),HttpStatus.CREATED);
    }

    @Log("修改${className}")
    @PutMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除${className}")
    @DeleteMapping(value = "/${changeClassName}/{${pkChangeColName}}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_DELETE')")
    public ResponseEntity delete(@PathVariable ${pkColumnType} ${pkChangeColName}){
        ${changeClassName}Service.delete(${pkChangeColName});
        return new ResponseEntity(HttpStatus.OK);
    }
}