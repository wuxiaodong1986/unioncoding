package com.unioncoding.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * 自定义sql查询
 * Created by 吴晓冬 on 2017/11/21.
 */
@Repository
public class SqlRepository
{
    @Autowired
    private EntityManager entityManager;

    /**
     * 自定义sql分页查询
     * 效率不高，表内数据极多时慎用
     * @param sql 自定义sql
     * @param pageRequest 分页信息
     * @param args sql中的参数
     * @return
     */
    @Transactional
    public Page getBySql(String sql, PageRequest pageRequest, Object... args)
    {
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < args.length; i++)
        {
            query.setParameter(i + 1, args[i]);
        }
        //第一次查询，查询出所有记录统计总数
        int total = query.getResultList().size();

        query.setFirstResult((pageRequest.getPageNumber()-1) * pageRequest.getPageSize()).setMaxResults(pageRequest.getPageSize());
        //第二次查询，查询出所需要的页数下的内容
        List list = query.getResultList();

        Page page = new PageImpl(list, pageRequest, total);

        return page;
    }
}
