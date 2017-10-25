package com.unioncoding.dao;

import com.unioncoding.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 吴晓冬 on 2017/10/25.
 */
public interface ProjectRepository extends JpaRepository<Project, Long>
{

}
