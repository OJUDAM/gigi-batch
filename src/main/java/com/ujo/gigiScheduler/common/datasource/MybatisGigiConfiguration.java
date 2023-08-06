//package com.ujo.test.common.datasource;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configurable
//@MapperScan(basePackages = "com.ujo.test.mapper", sqlSessionFactoryRef = "primarySqlSessionFactory")
//@EnableTransactionManagement
//public class MybatisGigiConfiguration {
//    @Bean(name = "primaryDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.hikari.primary")
//    public DataSource primaryDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @Primary
//    @Bean(name = "primarySqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("primaryDataSource")DataSource primaryDataSource, ApplicationContext applicationContext) throws Exception {
//        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
//        sqlSession.setDataSource(primaryDataSource);
//        sqlSession.setTypeAliasesPackage("com.ujo.test");
//        sqlSession.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml")); //mybatis-config.xml의 경로
//        sqlSession.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/*.xml")); //쿼리문을 관리하는 mapper파일의 경로
//
//        return sqlSession.getObject();
//    }
//
//    @Bean(name = "primarySqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory primarySqlSessionFactory) throws Exception{
//        return new SqlSessionTemplate(primarySqlSessionFactory);
//    }
//}
