package ${package}.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.exception.BadRequestException;
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

    private static final String ENTITY_NAME = "${changeClassName}";

    @Log("查询${className}")
    @GetMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity get${className}s(${className}DTO resources, Pageable pageable){
        return new ResponseEntity(${changeClassName}QueryService.queryAll(resources,pageable),HttpStatus.OK);
    }

    @Log("新增${className}")
    @PostMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity create(@Validated @RequestBody ${className} resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(${changeClassName}Service.create(resources),HttpStatus.CREATED);
    }

    @Log("修改${className}")
    @PutMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity update(@Validated @RequestBody ${className} resources){
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME +" ID Can not be empty");
        }
        ${changeClassName}Service.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除${className}")
    @DeleteMapping(value = "/${changeClassName}/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id){
        ${changeClassName}Service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}