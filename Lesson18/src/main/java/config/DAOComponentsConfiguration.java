package config;


import dao.*;
import exceptions.TableNotExistsException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@Import(DataConfiguration.class)
@EnableTransactionManagement
public class DAOComponentsConfiguration {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setExceptionTranslator(new TableNotExistsException());
        return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcOperations parameterJdbcOperations(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public SimpleJdbcInsertOperations jdbcInsertRecipeOperations(DataSource dataSource){
        return new SimpleJdbcInsert(dataSource)
                .withTableName("RECIPE")
                .usingGeneratedKeyColumns("id");
    }

    @Bean
    public IngredientAmountDAO ingredientAmountDAO(JdbcTemplate jdbcTemplate,
                                                   NamedParameterJdbcOperations parameterJdbcOperations,
                                                   SimpleJdbcInsertOperations jdbcInsertIngredientAmountOperations){
        return new IngredientAmountDAOImpl(jdbcTemplate, parameterJdbcOperations, jdbcInsertIngredientAmountOperations);
    }

    @Bean
    public IngredientDAO ingredientDAO(JdbcTemplate jdbcTemplate,
                                       IngredientAmountDAO ingredientAmountDAO){
        return new IngredientDAOImpl(jdbcTemplate, ingredientAmountDAO);
    }

    @Bean
    public RecipeDAO recipeDAO(JdbcTemplate jdbcTemplate,
                               SimpleJdbcInsertOperations jdbcInsertRecipeOperations,
                               LobHandler lobHandler,
                               IngredientDAO ingredientDAO){
        return new RecipeDAOImpl(jdbcTemplate, jdbcInsertRecipeOperations, lobHandler, ingredientDAO);
    }
}
