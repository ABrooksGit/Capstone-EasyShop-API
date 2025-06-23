package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.configurations.DatabaseConfig;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{

    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        ArrayList<Category> allCategories = new ArrayList<>();
        String sql = """
                Select category_id, name, description From categories
                """;


        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet results = preparedStatement.executeQuery();

        while(results.next()){
            int categoryID = results.getInt(1);
            String name = results.getString(2);
            String description = results.getString(3);
            Category category = new Category(categoryID,name,description);
            allCategories.add(category);


        }

        // get all categories
        return allCategories;
    }

    @Override
    public Category getById(int categoryId)
    {
        String sql = """
                Select category_id, name, description From categories
                Where category_id = ?
                
                
                """;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, categoryId);
            try(ResultSet results = preparedStatement.executeQuery()){
                while(results.next()){
                    int categoryID = results.getInt(1);
                    String name = results.getString(2);
                    String description = results.getString(3);
                    Category c = new Category(categoryID,name,description);
                    return c;



                }

            }

            }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // get category by id
        return null;



    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
