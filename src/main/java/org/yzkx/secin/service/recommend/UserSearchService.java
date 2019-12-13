package org.yzkx.secin.service.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yzkx.secin.mapper.recommend.UserSearchMapper;

@Service
public class UserSearchService {

	@Autowired
	private UserSearchMapper mapper;

}
