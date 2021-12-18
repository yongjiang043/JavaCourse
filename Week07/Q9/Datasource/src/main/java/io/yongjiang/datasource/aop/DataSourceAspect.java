package io.yongjiang.datasource.aop;

import io.yongjiang.datasource.datasource.DynamicDataSource;
import io.yongjiang.datasource.annotation.DataSourceSpec;
import io.yongjiang.datasource.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DataSourceAspect implements Ordered {

    @Pointcut("@annotation(io.yongjiang.datasource.annotation.DataSourceSpec)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSourceSpec ds = method.getAnnotation(DataSourceSpec.class);
        if (ds == null) {
            DynamicDataSource.setDataSource(Constants.MASTER);
            log.info("set datasource is " + Constants.MASTER);
        } else {
            DynamicDataSource.setDataSource(ds.name());
            log.info("set datasource is " + ds.name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            log.info("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}


