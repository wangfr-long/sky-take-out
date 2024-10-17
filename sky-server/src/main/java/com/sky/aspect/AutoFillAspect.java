package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    private void pt(){};

    @Before("pt()")
    public void autoFill(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//获得方法签名
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);//获得注解；2
        //获取参数对象
        Object[] args = joinPoint.getArgs();
        Object entity = args[0];
        //获取要赋的值
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //给参数对象的公共字段复制
        if (annotation.value()==OperationType.INSERT){
            //通过反射获取当前对象的方法；
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                 setCreateUser.invoke(entity,currentId);
                 setCreateTime.invoke(entity,now);
                 setUpdateTime.invoke(entity,now);
                 setUpdateUser.invoke(entity,currentId);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }else if (annotation.value()==OperationType.UPDATE){
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
