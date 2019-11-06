package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
public interface FileRepository extends JpaRepository<FileModel, String>, JpaSpecificationExecutor {

//    /**
//     * findByPid
//     * @param fileSortId
//     * @return
//     */
//    List<FileModel> findByFileSortId(String fileSortId);
//
//    @Query(value = "select name from file_sort union all select file_name as name from file where id = ?",nativeQuery = true)
//    String findAllFileAndSortNameById(String id);

//    Set<Dept> findByRoles_Id(Long id);
}