package com.rib.wxcs.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rib.wxcs.bean.WxUser;

public interface WxUserDAO
        extends PagingAndSortingRepository<WxUser, Long>, JpaSpecificationExecutor<WxUser> {

    @Query("select wu from WxUser wu where wu.name =?1 and wu.state=1")
    public WxUser findOneByName(String name);
}
