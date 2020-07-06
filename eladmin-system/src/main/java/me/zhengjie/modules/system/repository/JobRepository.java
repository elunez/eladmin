/*
 *  Copyright 2019-2020
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
package me.zhengjie.modules.system.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import me.zhengjie.base.BaseRepository;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.repository.jpa.JobJpaRepository;
import me.zhengjie.modules.system.repository.mp.JobService;
import me.zhengjie.utils.enums.DbType;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author liaojinlong
 * @since 2020/7/4 14:29
 */
@Repository
public class JobRepository extends BaseRepository<JobService, JobJpaRepository, Job, Long> {


    public JobRepository(JobService mpService, JobJpaRepository jpaRepository) {
        super(mpService, jpaRepository);
        setDbType(DbType.MYBATIS);
    }

    /**
     * @param ids
     */
    public void deleteAllByIdIn(Set<Long> ids) {
        switch (dbType) {
            case JPA:
                jpaRepository.deleteAllByIdIn(ids);
                break;
            case MYBATIS:
                mpService.remove(Wrappers.<Job>query().in("job_id", ids.toArray()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
    }

    /**
     * @param name
     * @return /
     */
    public Job findByName(String name) {
        Job result;
        switch (dbType) {
            case JPA:
                result = jpaRepository.findByName(name);
                break;
            case MYBATIS:
                result = mpService
                        .getOne(Wrappers.<Job>query().eq(true, "NAME", name));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dbType);
        }
        return result;
    }
}
