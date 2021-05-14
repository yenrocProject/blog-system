package com.yenroc.ho.blogic.java.user;

import com.yenroc.ho.blogic.restDto.user.UserCreateAndLoginReqt;
import com.yenroc.ho.blogic.restDto.user.UserCreateAndLoginResp;
import com.yenroc.ho.common.bean.SystemMessage;
import com.yenroc.ho.common.exception.BizLogicException;
import com.yenroc.ho.common.service.BizLogic;
import com.yenroc.ho.mapper.UserDao;
import com.yenroc.ho.mapper.entity.User;
import com.yenroc.ho.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UserCreateAndLoginBLogic")
@Transactional(rollbackFor = Exception.class)
public class UserCreateAndLoginBLogic implements BizLogic<UserCreateAndLoginReqt, UserCreateAndLoginResp> {

    private static final Logger log = LoggerFactory.getLogger(UserCreateAndLoginBLogic.class);


    @Autowired
    private UserDao userDao;

    @Override
    public UserCreateAndLoginResp execute(UserCreateAndLoginReqt param) throws Exception {
        UserCreateAndLoginResp result = new UserCreateAndLoginResp();

        if (StringUtils.isBlank(param.getUserName()) || StringUtils.isBlank(param.getPassword())) {
            throw new BizLogicException(new SystemMessage("USER_NAME_NOT_BLANK","用户名或密码不能为空！"));
        }
        List<User> selectResult = userDao.finByUserName(param.getUserName());
        if (selectResult.size() > 0) {
            User sqlUser = selectResult.get(0);
            if (!sqlUser.getPassword().equals(MD5Util.MD5(param.getPassword()))) {
                throw new BizLogicException(new SystemMessage("USER_PASSWOED_ERROR","用户名口令错误！"));
            } else {
                log.info("用户=[{}]登录成功!", sqlUser.getUserName());
            }
            result.setId(sqlUser.getId());
        } else {
            User user = new User();
            user.setUserName(param.getUserName());
            user.setStatus(0);
            user.setPassword(MD5Util.MD5(param.getPassword()));
            int i = userDao.insert(user);
            log.info("用户=[{}]创建成功,返回i={}", user.getUserName(), i);
            result.setId(i);
            result.setUserName(param.getUserName());
        }
        return result;
    }
}
