package com.example.authority.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.common.constant.RedisConstant;
import com.example.common.constant.utils.TokenSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    //    在请求处理之前调用,只有返回true才会执行要执行的请求
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setCharacterEncoding("UTF-8");
        String token=httpServletRequest.getHeader("accessToken");
        Map<String,Object> map=new HashMap<>();
        if (null==token){
            map.put("data","token is null");
            map.put("code","402");
            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            return false;
        }else {
            //校验该token是否失效
            String uuid = TokenSign.getUserId(token);
            String redisToken=(String) redisTemplate.opsForValue().get(RedisConstant.USER_TOKEN+uuid);
            if (redisToken==null){
                map.put("data","token is unauthorized");
                map.put("code","402");
                httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
                return false;
            }
            if (!redisToken.equals(token)){
                map.put("data","token is Invalid");
                map.put("code","402");
                httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
                return false;
            }
//            boolean result= TokenSign.verify(token);

//            if (result){
//                //更新存储的token信息
//                TokenConstant.updateTokenMap(token);
//                return true;
//            }

//            map.put("data","token is unauthorized");
//            map.put("code","402");
//            httpServletResponse.getWriter().write(JSONObject.toJSONString(map));
            return true;

        }


    }

    //    试图渲染之后执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //    在请求处理之后,视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}