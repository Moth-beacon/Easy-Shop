package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.ProfileDao;
import org.yearup.models.Profile;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, email, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, profile.getUserId());
            statement.setString(2, profile.getFirstName());
            statement.setString(3, profile.getLastName());
            statement.setString(4, profile.getEmail());
            statement.setString(5, profile.getPhone());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next())
            {
                int id = keys.getInt(1);
                return getById(id);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Profile> getByUserId(int userId)
    {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT * FROM profiles WHERE user_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();
            while (row.next())
            {
                profiles.add(mapRow(row));
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return profiles;
    }

    private Profile getById(int id)
    {
        String sql = "SELECT * FROM profiles WHERE profile_id = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet row = statement.executeQuery();
            if (row.next())
            {
                return mapRow(row);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return null;
    }

    private Profile mapRow(ResultSet row) throws SQLException
    {
        int profileId = row.getInt("profile_id");
        int userId = row.getInt("user_id");
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        String email = row.getString("email");
        String phone = row.getString("phone");

        return new Profile(profileId, userId, firstName, lastName, email, phone);
    }
}
