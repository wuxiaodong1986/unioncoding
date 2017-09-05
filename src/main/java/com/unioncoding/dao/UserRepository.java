package com.unioncoding.dao;

import com.unioncoding.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 吴晓冬 on 2017/9/2.
 */
public interface UserRepository extends JpaRepository<User, Long>
{
    public User findByUsername(String username);
}
