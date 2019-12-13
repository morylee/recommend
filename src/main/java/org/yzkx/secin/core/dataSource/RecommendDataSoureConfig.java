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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = "org.yzkx.secin.mapper.recommend", sqlSessionFactoryRef = "recommendSqlSessionFactory")
public class RecommendDataSoureConfig {

	@Autowired
	private RecommendDuridBean recommendDuridBean;
	
	@Bean(name = "recommendDataSource")
	@Primary
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		
		dataSource.setUrl(this.recommendDuridBean.getUrl());
		dataSource.setUsername(this.recommendDuridBean.getUsername());
		dataSource.setPassword(this.recommendDuridBean.getPassword());
		dataSource.setDriverClassName(this.recommendDuridBean.getDriverClassName());
		
		dataSource.setInitialSize(this.recommendDuridBean.getInitialSize());
		dataSource.setMinIdle(this.recommendDuridBean.getMinIdle());
		dataSource.setMaxActive(this.recommendDuridBean.getMaxActive());
		dataSource.setMaxWait(this.recommendDuridBean.getMaxWait());
		
		return dataSource;
	}
	
	@Bean(name = "recommendDataSourceTransactionManager")
	@Primary
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("recommendDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean(name = "recommendSqlSessionFactory")
	@Primary
	public SqlSessionFactory sessionFactory(@Qualifier("recommendDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml");
		sessionFactoryBean.setMapperLocations(resources);
		return sessionFactoryBean.getObject();
	}
	
	@Bean(name = "recommendSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("recommendSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
