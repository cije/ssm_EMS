package com.ce.controller;

import com.ce.domain.SysLog;
import com.ce.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Pointcut("execution(* com.ce.controller.*.*(..))")
    public void pt() {
    }

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 开始时间
     */
    private Date visitTime;
    /**
     * 访问的类
     */
    private Class clazz;
    /**
     * 访问的方法
     */
    private Method method;

    /**
     * 前置通知
     * 主要获取开始时间，执行的是哪一个类，执行的是哪个方法
     */
    @Before("pt()")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        // 当前时间就是开始访问时间
        visitTime = new Date();
        // 具体要访问的类
        clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        // 获取访问的方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取具体执行的方法Method对象
        if (args == null || args.length == 0) {
            // 只能获取无参数的方法
            method = clazz.getMethod(methodName);
        } else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            method = clazz.getMethod(methodName, classArgs);
        }
    }

    /**
     * 后置通知
     */
    @After("pt()")
    public void doAfter(JoinPoint joinPoint) {
        // 获取访问的时长
        long time = System.currentTimeMillis() - visitTime.getTime();
        // 获取url
        String url = "";
        if (clazz != null && method != null && clazz != LogAop.class) {
            // 1.获取类上的@RequestMapping
            RequestMapping clazzAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (clazzAnnotation != null) {
                String[] classValue = clazzAnnotation.value();
                if (classValue.length == 0 || "/sysLog".equals(classValue[0])) {
                    return;
                }
                // 2.获取方法上的@RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];

                    // 获取访问的ip地址
                    String ip = request.getRemoteAddr();

                    // 获取当前操作的用户
                    SecurityContext context = SecurityContextHolder.getContext();
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    // 将日志相关信息封装到SysLog对象
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    sysLog.setMethod("[类名] " + clazz.getSimpleName() + " [方法名] " + method.getName());
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}
