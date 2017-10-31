package com.unioncoding.dao;

import com.unioncoding.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 吴晓冬 on 2017/10/30.
 */
public interface IssueRepository extends JpaRepository<Issue, Long>
{

}
