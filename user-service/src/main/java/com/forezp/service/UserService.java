
package com.forezp.service;

import com.forezp.client.AuthServiceClient;
import com.forezp.dao.UserDao;
import com.forezp.dto.LoginDTO;
import com.forezp.dto.RespDTO;
import com.forezp.entity.JWT;
import com.forezp.entity.User;
import com.forezp.exception.CommonException;
import com.forezp.exception.ErrorCode;
import com.forezp.util.BPwdEncoderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao userDao = null;

	@Autowired
	private AuthServiceClient authServiceClient = null;

	public User createUser(User user) {
		return userDao.save(user);
	}

	public User getUserInfo(String username) {
		return userDao.findByUsername(username);
	}

	@SuppressWarnings("rawtypes")
	public RespDTO login(String username, String password) {
		User user = userDao.findByUsername(username);
		if (null == user) {
			throw new CommonException(ErrorCode.USER_NOT_FOUND);
		}
		if (!BPwdEncoderUtils.matches(password, user.getPassword())) {
			throw new CommonException(ErrorCode.USER_PASSWORD_ERROR);
		}
		JWT jwt = authServiceClient.getToken("Basic dWFhLXNlcnZpY2U6MTIzNDU2", "password", username, password);
		// 获得用户菜单
		if (null == jwt) {
			throw new CommonException(ErrorCode.GET_TOKEN_FAIL);
		}
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setUser(user);
		loginDTO.setToken(jwt.getAccess_token());
		return RespDTO.onSuc(loginDTO);
	}
}