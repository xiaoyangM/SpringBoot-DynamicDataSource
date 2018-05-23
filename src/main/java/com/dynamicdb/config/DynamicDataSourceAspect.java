package com.dynamicdb.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切换的切面，切 DAO 层，通过 DAO 层方法名判断使用哪个数据源，实现数据源切换
 * */

@Aspect
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("execution(* com.dynamicdb.dao..*.select*(..))")
    public void useSlaveDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.useSlaveDataSource();
        logger.info("Switch DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

    @Before("execution(* com.dynamicdb.dao..*.insert*(..))")
    public void useMasterDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.useMasterDataSource();
        logger.info("Switch DataSource to [{}] in Method [{}]",
                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

//    @After("execution(* com.dynamicdb.dao..*.select*(..))")
//    public void restoreDataSource(JoinPoint point) {
//        DynamicDataSourceContextHolder.clearDataSourceKey();
//        logger.info("Restore DataSource to [{}] in Method [{}]",
//                DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
//    }

}
