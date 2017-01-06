package com.rib.bt.repositiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rib.bt.Entity.BtResource;


public interface BtResourceDAO  extends PagingAndSortingRepository<BtResource, Long>, JpaSpecificationExecutor<BtResource> {

	@Query("select br from BtResource br where br.btCode =?1 and br.state=1")
	List<BtResource> findByBtCode(String btCode);

}
