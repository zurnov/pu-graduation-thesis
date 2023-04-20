package com.zurnov.restaurant.util.converter.dto;

import com.zurnov.restaurant.dto.order.OrderCreateDTO;
import com.zurnov.restaurant.dto.order.OrderDTO;
import com.zurnov.restaurant.dto.order.OrderStatusDTO;
import com.zurnov.restaurant.dto.order_product.OrderProductDTO;
import com.zurnov.restaurant.dto.restaurantTable.RestaurantTableDTO;
import com.zurnov.restaurant.dto.user.UserDTO;
import com.zurnov.restaurant.model.Order;
import com.zurnov.restaurant.model.OrderProduct;
import com.zurnov.restaurant.model.OrderStatus;
import com.zurnov.restaurant.model.RestaurantTable;
import com.zurnov.restaurant.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderDtoConverter {

    /**
     * Converts order entity object to order DTO object.
     *
     * @param order source object of type {@link Order}
     * @return orderDTO DTO converted from the source order Entity object.
     */
    public static OrderDTO toOrderDTO(Order order) {

        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());

        RestaurantTableDTO restaurantTableDTO = RestaurantTableDtoConverter.convertEntityToDto(order.getRestaurantTable());
        orderDTO.setRestaurantTable(restaurantTableDTO);

        UserDTO userDTO = UserDtoConverter.convertEntityToDto(order.getUser());
        orderDTO.setUser(userDTO);

        OrderStatusDTO orderStatusDTO = OrderStatusDtoConverter.convertEntityToDto(order.getOrderStatus());
        orderDTO.setOrderStatus(orderStatusDTO);

        orderDTO.setCreateDate(order.getCreateDate());

        List<OrderProductDTO> orderProducts = order.getOrderProducts()
                .stream()
                .map(OrderDtoConverter::toOrderProductDTO)
                .collect(Collectors.toList());
        orderDTO.setOrderProduct(orderProducts);

        orderDTO.setTotalPrice(order.getTotalPrice());

        return orderDTO;
    }

    /**
     * Converts order DTO object to order Entity.
     *
     * @param orderDTO source object of type {@link OrderDTO}
     * @return order Entity converted from the source order DTO object.
     */
    public static Order toOrderEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());

        order.setCreateDate(orderDTO.getCreateDate());

        RestaurantTable restaurantTable = RestaurantTableDtoConverter.convertDtoToEntity(orderDTO.getRestaurantTable());
        order.setRestaurantTable(restaurantTable);

        User user = UserDtoConverter.convertDtoToEntity(orderDTO.getUser());
        order.setUser(user);

        OrderStatus orderStatus = OrderStatusDtoConverter.convertDtoToEntity(orderDTO.getOrderStatus());
        order.setOrderStatus(orderStatus);

        List<OrderProduct> orderProducts = orderDTO.getOrderProduct()
                .stream()
                .map(OrderDtoConverter::toOrderProductEntity)
                .collect(Collectors.toList());
        order.setOrderProducts(orderProducts);

        return order;
    }


    /**
     * Converts order DTO object to order Entity.
     *
     * @param orderCreateDTO source object of type {@link OrderCreateDTO}
     * @return order Entity converted from the source order DTO object.
     */
    public static Order toOrderEntity(OrderCreateDTO orderCreateDTO) {
        Order order = new Order();

        RestaurantTable restaurantTable = RestaurantTableDtoConverter.convertDtoToEntity(orderCreateDTO.getRestaurantTable());
        order.setRestaurantTable(restaurantTable);

        User user = UserDtoConverter.convertDtoToEntity(orderCreateDTO.getUser());
        order.setUser(user);

        OrderStatus orderStatus = OrderStatusDtoConverter.convertDtoToEntity(orderCreateDTO.getOrderStatus());
        order.setOrderStatus(orderStatus);

        List<OrderProduct> orderProducts = orderCreateDTO.getOrderProducts()
                .stream()
                .map(OrderDtoConverter::toOrderProductEntity)
                .collect(Collectors.toList());

        order.setOrderProducts(orderProducts);
        
        return order;
    }

    /**
     * Converts order product entity object to order product DTO object.
     *
     * @param orderProduct source object of type {@link OrderProduct}
     * @return OrderProductDTO DTO converted from the source order Entity object.
     */
    private static OrderProductDTO toOrderProductDTO(OrderProduct orderProduct) {
        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setProduct(ProductDtoConverter.toProductDTO(orderProduct.getProduct()));
        orderProductDTO.setQuantity(orderProduct.getQuantity());

        return orderProductDTO;
    }

    /**
     * Converts order product DTO object to order product Entity.
     *
     * @param orderProductDTO source object of type {@link OrderProductDTO}
     * @return order Entity converted from the source order DTO object.
     */
    private static OrderProduct toOrderProductEntity(OrderProductDTO orderProductDTO) {
        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setProduct(ProductDtoConverter.toProductEntity(orderProductDTO.getProduct()));
        orderProduct.setQuantity(orderProductDTO.getQuantity());

        return orderProduct;
    }
}
