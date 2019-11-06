package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.FileSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
public interface FileSortRepository extends JpaRepository<FileSort, String>, JpaSpecificationExecutor {

    /**
     * findByPid
     * @param id
     * @return
     */
    List<FileSort> findByPid(String id);

    @Query(value = "select name from file_sort where id = ?",nativeQuery = true)
    String findNameById(String id);

//    Set<Dept> findByRoles_Id(Long id);
}