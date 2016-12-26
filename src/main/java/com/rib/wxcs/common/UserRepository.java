/**
 * @（#）:UserRepository.java
 * @description: 
 * @author: longshan 2015年6月11日
 * @version: Version 1.0
 */
package com.rib.wxcs.common;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Repository
@Transactional(readOnly = true)
public class UserRepository<T> {
    @PersistenceContext
    private EntityManager em;

    public Page<T> queryByCondition(String hql, Map<String, Object> param, Pageable pageRequest) {
        Query q = em.createQuery(hql);
        int index = 0;
        if (hql.indexOf("from") != -1) {
            index = hql.indexOf("from");
        } else if (hql.indexOf("FROM") != -1) {
            index = hql.indexOf("FROM");
        }
        String countSql = hql.substring(index);
        countSql = "select count(*) " + countSql;
        Query countQ = em.createQuery(countSql, Long.class);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
            countQ.setParameter(entry.getKey(), entry.getValue());
        }
        Assert.notNull(q);
        Assert.notNull(countQ);
        List<Long> totals = countQ.getResultList();
        Long total = 0L;
        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        q.setFirstResult(pageRequest.getOffset());
        q.setMaxResults(pageRequest.getPageSize());
        List<T> content = total > pageRequest.getOffset() ? q.getResultList()
                : Collections.<T> emptyList();
        return new PageImpl<T>(content, pageRequest, total);
    }

    public Page<T> queryByCondition(String hql, Map<String, Object> param, Pageable pageRequest,
            Class<T> resultClass) {
        Query q = em.createQuery(hql, resultClass);
        int index = 0;
        if (hql.indexOf("from") != -1) {
            index = hql.indexOf("from");
        } else if (hql.indexOf("FROM") != -1) {
            index = hql.indexOf("FROM");
        }
        String countSql = hql.substring(index);
        countSql = "select count(*) " + countSql;
        Query countQ = em.createQuery(countSql, Long.class);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
            countQ.setParameter(entry.getKey(), entry.getValue());
        }
        Assert.notNull(q);
        Assert.notNull(countQ);
        List<Long> totals = countQ.getResultList();
        Long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        q.setFirstResult(pageRequest.getOffset());
        q.setMaxResults(pageRequest.getPageSize());
        List<T> content = total > pageRequest.getOffset() ? q.getResultList()
                : Collections.<T> emptyList();
        return new PageImpl<T>(content, pageRequest, total);
    }

    public Page<T> queryByConditionNQ(String sql, Map<String, Object> param, Pageable pageRequest) {
        Query q = em.createNativeQuery(sql);
        String countSql = "select count(*) from (" + sql + ") count_sql";
        Query countQ = em.createNativeQuery(countSql);
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
            countQ.setParameter(entry.getKey(), entry.getValue());
        }
        Assert.notNull(q);
        Assert.notNull(countQ);
        List<Object> totals = countQ.getResultList();
        Long total = 0L;
        for (Object elementObj : totals) {
            BigInteger elementBi = (BigInteger) elementObj;
            Long element = elementBi.longValue();
            total += element == null ? 0 : element;
        }

        q.setFirstResult(pageRequest.getOffset());
        q.setMaxResults(pageRequest.getPageSize());
        List<T> content = total > pageRequest.getOffset() ? q.getResultList()
                : Collections.<T> emptyList();
        return new PageImpl<T>(content, pageRequest, total);
    }
    
    public Page<T> queryByConditionNQMap(String sql, Map<String, Object> param, Pageable pageRequest) {
        Query q = em.createNativeQuery(sql);
        q.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        String countSql = "select count(*) from (" + sql + ") count_sql";
        Query countQ = em.createNativeQuery(countSql);
        if(null != param){
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
                countQ.setParameter(entry.getKey(), entry.getValue());
            }
        }

        Assert.notNull(q);
        Assert.notNull(countQ);
        List<Object> totals = countQ.getResultList();
        Long total = 0L;
        for (Object elementObj : totals) {
            BigInteger elementBi = (BigInteger) elementObj;
            Long element = elementBi.longValue();
            total += element == null ? 0 : element;
        }

        q.setFirstResult(pageRequest.getOffset());
        q.setMaxResults(pageRequest.getPageSize());
        List<T> content = total > pageRequest.getOffset() ? q.getResultList()
                : Collections.<T> emptyList();
        return new PageImpl<T>(content, pageRequest, total);
    }

    public List queryByConditionNQ(String sql, Map<String, Object> param) {
        Query q = em.createNativeQuery(sql);
        
        if(null != param){
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }

        Assert.notNull(q);

        return q.getResultList();
    }

    public List<Object[]> findPassageParamByProjectId(String sql, String projectId) {
        Query q = em.createNativeQuery(sql);
        q.setParameter("projectId", projectId);

        return q.getResultList();
    }
    
    public List<Object[]> findFacePassagesByProjectId(String sql, String projectId){
        Query q = em.createNativeQuery(sql);
        if("".equals(projectId)||StringUtils.isBlank(projectId)){
            return q.getResultList();
        }
            q.setParameter("projectId", projectId);
            return q.getResultList();
    }

}
