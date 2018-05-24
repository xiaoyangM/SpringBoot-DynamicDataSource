package com.dynamicdb.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.dynamicdb.common.DataSourceKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类，在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 * */
@Configuration
public class DataSourceConfigurer {
    /**
     * 主库配置 master DataSource
     *
     * @return data source
     */
    @Bean("Master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }

    /**----------------------------从库配置，有几个从库就配置几个Bean------------------------------*/

    /**
     * SlaveFirst data source.
     *
     * @return the data source
     */
    @Bean("SlaveFirst")
    @ConfigurationProperties(prefix = "spring.datasource.slave-first")
    public DataSource slaveFirst() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * SlaveSecond data source.
     *
     * @return the data source
     */
    @Bean("SlaveSecond")
    @ConfigurationProperties(prefix = "spring.datasource.slave-second")
    public DataSource slaveSecond() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * 多数据的配置/负载均衡
     *
     * @return the data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        dataSourceMap.put(DataSourceKey.Master.name(), master());
        dataSourceMap.put(DataSourceKey.SlaveFirst.name(), slaveFirst());
        dataSourceMap.put(DataSourceKey.SlaveSecond.name(), slaveSecond());

        // 将主数据源Master设置为默认
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        // 将 master 和 slave 数据源作为指定的数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
        // 将 Slave 数据源的 key 放在集合中，用于轮循
        DynamicDataSourceContextHolder.slaveDataSourceKeys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.slaveDataSourceKeys.remove(DataSourceKey.Master.name());
        return dynamicRoutingDataSource;
    }


    /**
     * 注入 DataSourceTransactionManager 用于事务管理
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    /**
     * 将数据源注入 JdbcTemplate
     * */
    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dynamicDataSource());
        return jdbcTemplate;
    }
}
