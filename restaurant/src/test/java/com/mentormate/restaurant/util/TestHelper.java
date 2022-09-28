package com.mentormate.restaurant.util;

import com.mentormate.restaurant.model.Order;
import com.mentormate.restaurant.model.OrderProduct;
import com.mentormate.restaurant.model.OrderProductId;
import com.mentormate.restaurant.model.OrderStatus;
import com.mentormate.restaurant.model.Product;
import com.mentormate.restaurant.model.ProductCategory;
import com.mentormate.restaurant.model.RestaurantTable;
import com.mentormate.restaurant.model.Role;
import com.mentormate.restaurant.model.User;
import com.mentormate.restaurant.model.enumeration.OrderEnum;
import com.mentormate.restaurant.model.enumeration.RoleEnum;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class TestHelper {

    public static final int ID_1 = 1;
    public static final int ID_2 = 2;
    public static final int RESTAURANT_TABLE_NUMBER = 1;
    public static final int ORDER_PRODUCTS_QUANTITY = 1;
    public static final int PAGE_0 = 0;
    public static final int PAGE_5 = 5;
    public static final int PAGE_SIZE_1 = 1;
    
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(15.00);
    
    public static final PageRequest PAGE_REQUEST = PageRequest.of(PAGE_0, PAGE_SIZE_1);
    
    public static final OffsetDateTime OFFSET_DATE_TIME = OffsetDateTime.now();
    public static final OffsetDateTime OFFSET_DATE_TIME_MINUS_TWO_DAYS = OffsetDateTime.now().minusDays(2);

    public static final boolean IS_DELETED_FALSE = false;
    public static final boolean IS_DELETED_TRUE = true;

    public static final String USER_NAME = "Dimitar";
    public static final String USER_PASSWORD = "Password@";
    public static final String USER_EMAIL = "dimitar@mail.com";
    public static final String PRODUCT_CATEGORY_NAME = "Salad";
    public static final String PRODUCT_NAME = "Shoshska Salad";
    public static final String PRODUCT_DESCRIPTION = "Bulgarian salad";


    public static final RoleEnum ROLE_ENUM_ADMIN = RoleEnum.ADMIN;
    public static final RoleEnum ROLE_ENUM_USER = RoleEnum.ADMIN;
    public static final OrderEnum ORDER_STATUS_ENUM = OrderEnum.ACTIVE;

    public static User createUser(Integer id, String name, String password, String email, boolean isUserDeleted, Role role) {

        List<Role> roles = List.of(role);

        User expectedUser = new User();
        expectedUser.setId(id);
        expectedUser.setName(name);
        expectedUser.setPassword(password);
        expectedUser.setEmail(email);
        expectedUser.setIsDeleted(isUserDeleted);
        expectedUser.setUserRoles(roles);

        return expectedUser;
    }

    public static Role createRole(Integer id, RoleEnum roleEnum) {
        Role role = new Role();
        role.setId(id);
        role.setRoleEnum(roleEnum);

        return role;
    }

    public static RestaurantTable createRestaurantTable(Integer id, int tableNumber) {
        RestaurantTable expectedRestaurantTable = new RestaurantTable();
        expectedRestaurantTable.setId(id);
        expectedRestaurantTable.setTableNumber(tableNumber);

        return expectedRestaurantTable;
    }

    public static OrderStatus createOrderStatus(Integer id, OrderEnum orderEnum) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(id);
        orderStatus.setOrderEnum(orderEnum);

        return orderStatus;
    }

    public static ProductCategory createProductCategory(Integer id, String name) {
        ProductCategory expectedProductCategory = new ProductCategory();
        expectedProductCategory.setId(id);
        expectedProductCategory.setName(name);

        return expectedProductCategory;
    }

    public static Product createProduct(Integer id,
                                        String name,
                                        boolean isDeleted,
                                        BigDecimal price,
                                        String description,
                                        ProductCategory productCategory) {

        Product expectedProduct = new Product();
        expectedProduct.setId(id);
        expectedProduct.setName(name);
        expectedProduct.setIsDeleted(isDeleted);
        expectedProduct.setPrice(price);
        expectedProduct.setDescription(description);
        expectedProduct.setProductCategory(productCategory);

        return expectedProduct;
    }

    public static Order createOrder(Integer id,
                                    RestaurantTable restaurantTable,
                                    User user,
                                    OffsetDateTime createDate,
                                    OrderStatus orderStatus,
                                    List<OrderProduct> orderProducts) {

        Order order = new Order();

        order.setId(id);
        order.setRestaurantTable(restaurantTable);
        order.setUser(user);
        order.setCreateDate(createDate);
        order.setOrderStatus(orderStatus);
        order.setOrderProducts(orderProducts);

        return order;
    }

    public static OrderProduct createOrderProduct(OrderProductId orderProductId,
                                                  Order order,
                                                  Product product,
                                                  Integer quantity) {
        
        OrderProduct orderProduct = new OrderProduct();
        
        orderProduct.setId(orderProductId);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        
        return orderProduct;
    }
    
    public static OrderProductId createOrderProductId(Integer orderId, Integer productId) {
        OrderProductId orderProductId = new OrderProductId();
        
        orderProductId.setOrderId(orderId);
        orderProductId.setProductId(productId);
        
        return orderProductId;
    }
}
