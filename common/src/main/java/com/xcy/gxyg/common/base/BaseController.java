package com.xcy.gxyg.common.base;

import com.xcy.gxyg.common.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhiMing
 */
@Slf4j
public class BaseController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;
    @Resource
    private JwtUtils jwtUtils;

    public Long getUserId() {
        String token = request.getHeader("token");
        return jwtUtils.getUserId(token);
    }

    public Long getRoleId() {
        String token = request.getHeader("token");
        return jwtUtils.getRoleId(token);
    }

    public UserInfoRes getUserInfo(Long userId) {
        return userService.getUserInfo(userId);
    }


    public String getIp() {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
