package org.yearup.data.mysql;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;
import org.yearup.models.User;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile editProfile(Profile profile) {

        String sql = """
                Update profiles
                SET first_name = ? , last_name = ?, phone = ?, email = ?, address = ?, city = ?, state = ?, zip = ?
                where user_id = ?

                """;



        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ){
            preparedStatement.setString(1,profile.getFirstName());
            preparedStatement.setString(2,profile.getLastName());
            preparedStatement.setString(3,profile.getPhone());
            preparedStatement.setString(4,profile.getEmail());
            preparedStatement.setString(5,profile.getAddress());
            preparedStatement.setString(6,profile.getCity());
            preparedStatement.setString(7,profile.getState());
            preparedStatement.setString(8,profile.getZip());
            preparedStatement.setInt(9, profile.getUserId());



            int rows = preparedStatement.executeUpdate();

                return profile;



        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public Profile getProfile(int userId) {

        String sql = """
                Select first_name, last_name, phone, email, address, city, state,
                 zip from profiles
                 where user_id = ?
         
                """;



        try(Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);


            ResultSet results = preparedStatement.executeQuery();

            if(results.next()){
                Profile profile = new Profile();

                profile.setFirstName(results.getString(1));
                profile.setLastName(results.getString(2));
                profile.setPhone(results.getString(3));
                profile.setEmail(results.getString(4));
                profile.setAddress(results.getString(5));
                profile.setCity(results.getString(6));
                profile.setState(results.getString(7));
                profile.setZip(results.getString(8));


                return profile;

            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return null;



    }
}
