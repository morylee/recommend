package org.yzkx.secin.core.dataSource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class QuartzDataSourceConfig {

	@Autowired
	private QuartzDuridBean quartzDuridBean;

	@Bean(name = "quartzDataSource")
	public DataSource dataSource () {
		DruidDataSource datasource = new DruidDataSource();
		
		datasource.setUrl(this.quartzDuridBean.getUrl());
		datasource.setUsername(this.quartzDuridBean.getUsername());
		datasource.setPassword(this.quartzDuridBean.getPassword());
		datasource.setDriverClassName(this.quartzDuridBean.getDriverClassName());
		
		datasource.setInitialSize(this.quartzDuridBean.getInitialSize());
		datasource.setMinIdle(this.quartzDuridBean.getMinIdle());
		datasource.setMaxActive(this.quartzDuridBean.getMaxActive());
		datasource.setMaxWait(this.quartzDuridBean.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(this.quartzDuridBean.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(this.quartzDuridBean.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(this.quartzDuridBean.getValidationQuery());
		datasource.setTestWhileIdle(this.quartzDuridBean.getTestWhileIdle());
		datasource.setTestOnBorrow(this.quartzDuridBean.getTestOnBorrow());
		datasource.setTestOnReturn(this.quartzDuridBean.getTestOnReturn());
		
		return datasource;
	}

}
