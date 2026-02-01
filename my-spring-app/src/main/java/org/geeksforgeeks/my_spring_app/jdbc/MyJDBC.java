package org.geeksforgeeks.my_spring_app.jdbc;

import org.geeksforgeeks.my_spring_app.mappers.ItemRowMapper;
import org.geeksforgeeks.my_spring_app.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class MyJDBC {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Item> readItemsFromDB()  {
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM items");
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        List<Item> itemList = new ArrayList<>();
//        while (resultSet.next()) {
//            Item item = new Item(
//                    resultSet.getInt("id"),
//                    resultSet.getString("name"),
//                    resultSet.getDouble("price"));
//            itemList.add(item);
//        }
//        return itemList;

        return this.jdbcTemplate.query("SELECT * FROM items", new ItemRowMapper());
    }

    public Item addItem(Item item, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO items VALUES (?, ?, ?)");
        ps.setInt(1, item.getId());
        ps.setString(2, item.getName());
        ps.setDouble(3, item.getPrice());

        ps.execute();
        return item;
    }

    public Item updateItem(Item item, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("UPDATE items SET name ='"
                + item.getName() + "', price=" + item.getPrice() + " WHERE id=" + item.getId());
        return item;
    }
}

// ddl - data definition language // CREATE, ALTER, DROP, TRUNCATE, RENAME
// dml - data manipulation language // INSERT, SELECT, UPDATE, DELETE
