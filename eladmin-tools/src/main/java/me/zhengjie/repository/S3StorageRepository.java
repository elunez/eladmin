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
package me.zhengjie.repository;

import me.zhengjie.domain.S3Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* @author Zheng Jie
* @date 2025-06-25
*/
public interface S3StorageRepository extends JpaRepository<S3Storage, Long>, JpaSpecificationExecutor<S3Storage> {

	/**
	 * 根据ID查询文件路径
	 * @param id 文件ID
	 * @return 文件路径
	 */
	@Query(value = "SELECT file_path FROM s3_storage WHERE id = ?1", nativeQuery = true)
	String selectFilePathById(Long id);
}