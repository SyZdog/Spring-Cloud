package com.jt.service;

import java.util.List;

import com.jt.vo.EasyUITree;

public interface ItemCatService {

	String findItemCatName(Long itemCatId);

	List<EasyUITree> findEasyUITreeByParentId(Long parentId);

	List<EasyUITree> findItemCatListByCache(Long parentId);

}
