package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.configurations.DatabaseConfig;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
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


        try(Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet results = preparedStatement.executeQuery();
        ) {

            while (results.next()) {
                int categoryID = results.getInt(1);
                String name = results.getString(2);
                String description = results.getString(3);
                Category category = new Category(categoryID, name, description);
                allCategories.add(category);


            }
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

            }   catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        // get category by id
        return null;



    }

    @Override
    public Category create(Category category)
    {
        String sql = """
                INSERT INTO categories (name, description)
                VALUES  (?, ?)
                """;

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            int rows = preparedStatement.executeUpdate();

            if(rows > 0){
                ResultSet results = preparedStatement.getGeneratedKeys();
                if(results.next()){
                    int id = results.getInt(1);

                    return getById(id);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        String sql = """
                UPDATE name, description from categories
                Where category_id = ?
                """;
        // update category
        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
        String sql = """
                DELETE FROM categories WHERE category_id = ?
                
                """;
        try(Connection connection = getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
