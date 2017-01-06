package com.rib.bt.repositiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rib.bt.Entity.ResourceLink;


public interface ResourceLinkDAO  extends PagingAndSortingRepository<ResourceLink, Long>, JpaSpecificationExecutor<ResourceLink> {

	@Query("select rl from ResourceLink rl,BtResource br where rl.resourceId=?1 and br.id=rl.resourceId and br.state=1")
	List<ResourceLink> findByResourceId(Long resourceId);

}
