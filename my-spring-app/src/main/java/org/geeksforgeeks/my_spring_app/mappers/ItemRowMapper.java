package org.geeksforgeeks.my_spring_app.mappers;

import org.geeksforgeeks.my_spring_app.models.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<Item> {

    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"));
    }
}
