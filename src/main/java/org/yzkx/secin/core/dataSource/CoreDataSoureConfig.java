package org.yzkx.secin.core.dataSource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "org.yzkx.secin.mapper.core", sqlSessionFactoryRef = "coreSqlSessionFactory")
public class CoreDataSoureConfig {

	@Autowired
	private CoreDuridBean coreDuridBean;
	
	@Bean(name = "coreDataSource")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		
		dataSource.setUrl(this.coreDuridBean.getUrl());
		dataSource.setUsername(this.coreDuridBean.getUsername());
		dataSource.setPassword(this.coreDuridBean.getPassword());
		dataSource.setDriverClassName(this.coreDuridBean.getDriverClassName());
		
		dataSource.setInitialSize(this.coreDuridBean.getInitialSize());
		dataSource.setMinIdle(this.coreDuridBean.getMinIdle());
		dataSource.setMaxActive(this.coreDuridBean.getMaxActive());
		dataSource.setMaxWait(this.coreDuridBean.getMaxWait());
		
		return dataSource;
	}
	
	@Bean(name = "coreDataSourceTransactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("coreDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "coreSqlSessionFactory")
	public SqlSessionFactory sessionFactory(@Qualifier("coreDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml");
		sessionFactoryBean.setMapperLocations(resources);
		return sessionFactoryBean.getObject();
	}
	
	@Bean(name = "coreSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("coreSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
