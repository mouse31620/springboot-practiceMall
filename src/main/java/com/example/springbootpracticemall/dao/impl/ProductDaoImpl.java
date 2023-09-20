package com.example.springbootpracticemall.dao.impl;

import com.example.springbootpracticemall.dao.ProductDao;
import com.example.springbootpracticemall.model.Product;
import com.example.springbootpracticemall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer productId) {
        String sql = "select product_id, product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "from product where product_id = :productId;";
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList =namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        if (productList.size() > 0) {
            return productList.get(0);
        } else {
            return null;
        }

    }
}
