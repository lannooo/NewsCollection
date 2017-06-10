package cn.lannooo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by 51499 on 2017/4/8 0008.
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{
    public final static String SESSION_KEY_USER = "user";

    @Bean
    public SecurityInterceptor getSecurityInterceptor(){
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry){
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/user/**");
        addInterceptor.excludePathPatterns("/test**");
        addInterceptor.excludePathPatterns("/news/**");
        addInterceptor.excludePathPatterns("/index");
        addInterceptor.addPathPatterns("/**");
    }


    private class SecurityInterceptor extends HandlerInterceptorAdapter{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            HttpSession session = request.getSession();

            //如果是ajax请求响应头会有x-requested-with
            if (request.getHeader("x-requested-with") != null &&
                    request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                if(null==session.getAttribute(SESSION_KEY_USER)) {
                    PrintWriter out = response.getWriter();
                    out.print("noSession");//session失效
                    out.flush();
                    return false;
                }
                return true;
            }
            else{
                //非ajax请求时，session失效的处理
                if(null!=session.getAttribute(SESSION_KEY_USER))
                    return true;
                String url = "/index";
                response.sendRedirect(url);
                return false;
            }
        }
    }
}
