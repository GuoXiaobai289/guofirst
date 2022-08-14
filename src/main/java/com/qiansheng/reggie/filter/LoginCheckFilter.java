package com.qiansheng.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.qiansheng.reggie.controller.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(asyncSupported = true,urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest request = (HttpServletRequest) servletRequest;
       HttpServletResponse response= (HttpServletResponse) servletResponse;

       //获取本次请求的uri
       String requestURI = request.getRequestURI();
       log.info(requestURI);

       //判断本次请求是否处理
          //不需要处理的路径
        String[] uris=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/inform/**",
                "/user/sendMsg",
                "/user/login",
                "/sse/**",
                "/front/**"
        };
        boolean check = check(uris, requestURI);

        if (!check) {
            //需要处理：判断是否登录
            if(request.getSession().getAttribute("employee")!=null){
                //如果已经登陆：放行
                log.info("已经登陆！放行");
                filterChain.doFilter(request,response);
            }else if(request.getSession().getAttribute("user")!=null){
                log.info("已经登陆！放行");
                filterChain.doFilter(request,response);
            }else  {
                //未登录:做出响应
                log.info("检测到未登录");
                response.getWriter().print(JSON.toJSONString(R.error("NOTLOGIN")));
                return;
            }
        } else {
            //如果不需要处理：放行
            log.info("不需要处理！放行");
            filterChain.doFilter(request,response);
        }
    }

    /**
     * 判断路径是否匹配
     * @param uris
     * @param path
     * @return
     */
    public boolean check(String[] uris,String path){
        for (String s : uris) {
            boolean match = PATH_MATCHER.match(s, path);
            if(match){
                return true;
            }
        }
        return false;
    }
}
