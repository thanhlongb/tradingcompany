package vn.edu.rmit.tradingcompany.test;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import vn.edu.rmit.tradingcompany.model.*;
import org.junit.Assert;
import org.junit.Test;
import vn.edu.rmit.tradingcompany.util.TestUtil;

import java.io.IOException;
import java.util.List;

public class OrderTests {
    @Test
    public void testGetOrder() throws IOException {
        // add new order for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Order addedOrder = TestUtil.addNewOrderForTest(staff);

        // test get order
        String url = String.format(TestConfig.URL_ORDER + "/%d", addedOrder.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Order gotOrder = TestUtil.modifiedGson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertEquals(addedOrder.getId(), gotOrder.getId());

        // delete unit test order after added
        TestUtil.deleteTestOrder(addedOrder);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testGetOrder_NonExistId() throws IOException {
        // test get order
        String url = String.format(TestConfig.URL_ORDER + "/%d", 99999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Order gotOrder = TestUtil.modifiedGson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(gotOrder);
    }

    @Test
    public void testGetOrder_NegativeId() throws IOException {
        // test get order
        String url = String.format(TestConfig.URL_ORDER + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Order gotOrder = TestUtil.modifiedGson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(gotOrder);
    }

    @Test(expected = IOException.class)
    public void testGetOrder_NonNumericId() throws IOException {
        // test get order
        String url = String.format(TestConfig.URL_ORDER + "/%s", "nonumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Order gotOrder = TestUtil.modifiedGson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(gotOrder);
    }

    @Test(expected = IOException.class)
    public void testGetOrder_SpecialCharacterId() throws IOException {
        // test get order
        String url = String.format(TestConfig.URL_ORDER + "/%s", "*&*(");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Order gotOrder = TestUtil.modifiedGson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(gotOrder);
    }

    @Test
    public void testGetAllOrder() throws IOException {
        // add new order for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Order addedOrder = TestUtil.addNewOrderForTest(staff);

        // test get all orders
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, "", "GET");
        List<Order> orders = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<Order>>(){}.getType());
        Assert.assertTrue(orders.size() >= 1);

        // delete unit test order after added
        TestUtil.deleteTestOrder(addedOrder);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testCreateOrder() throws IOException {
        // test add new order
        Staff staff = TestUtil.addNewStaffForTest();
        String testData = String.format("{\n" +
                "\t\"staff\": {\"id\": %d}\n" +
                "}", staff.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, testData, "POST");
        Order createdOrder = TestUtil.modifiedGson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertEquals(staff.getId(), createdOrder.getStaff().getId());

        // delete unit test order after added
        TestUtil.deleteTestOrder(createdOrder);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testDeleteOrder() throws IOException {
        // add new order for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Order addedOrder = TestUtil.addNewOrderForTest(staff);

        // test delete order
        String deleteData = String.format("{\"id\": %d}", addedOrder.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, deleteData, "DELETE");
        Order deletedOrder = new Gson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertEquals(addedOrder.getId(), deletedOrder.getId());

        TestUtil.deleteTestStaff(staff);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrder_NonExistId() throws IOException {
        // test delete order
        String deleteData = String.format("{\"id\": %d}", 99999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, deleteData, "DELETE");
        Order deletedOrder = new Gson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(deletedOrder);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrder_NegativeId() throws IOException {
        // test delete order
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, deleteData, "DELETE");
        Order deletedOrder = new Gson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(deletedOrder);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrder_NonNumeric() throws IOException {
        // test delete order
        String deleteData = String.format("{\"id\": %s}", "nonumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, deleteData, "DELETE");
        Order deletedOrder = new Gson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(deletedOrder);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrder_SpecialCharacterId() throws IOException {
        // test delete order
        String deleteData = String.format("{\"id\": %s}", "*(*&*");
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, deleteData, "DELETE");
        Order deletedOrder = new Gson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertNull(deletedOrder);
    }

    @Test
    public void testUpdateOrder() throws IOException {
        // add new order for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Order addedOrder = TestUtil.addNewOrderForTest(staff);
        Staff newStaff = TestUtil.addNewStaffForTest();

        // test update order
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"staff\": {\n" +
                "        \"id\": %d\n" +
                "    }\n" +
                "}", addedOrder.getId(), newStaff.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER, updateData, "PUT");
        Order updatedOrder = new Gson().fromJson(json, new TypeToken<Order>(){}.getType());
        Assert.assertEquals(newStaff.getId(), updatedOrder.getStaff().getId());

        // delete unit test order after added
        TestUtil.deleteTestOrder(addedOrder);
        TestUtil.deleteTestStaff(newStaff);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testGetOrderDetail() throws IOException {
        // add new orderDetail for testing
        Product product = TestUtil.addNewProductForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Order order = TestUtil.addNewOrderForTest(staff);
        OrderDetail addedOrderDetail = TestUtil.addNewOrderDetailForTest(order, product);

        // test get orderDetail
        String url = String.format(TestConfig.URL_ORDER_DETAIL + "/%d", addedOrderDetail.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        OrderDetail gotOrderDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertEquals(addedOrderDetail.getId(), gotOrderDetail.getId());

        // delete unit test orderDetail after added
        TestUtil.deleteTestOrderDetail(addedOrderDetail);
        TestUtil.deleteTestOrder(order);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testGetOrderDetail_NonExistId() throws IOException {
        // test get orderDetail
        String url = String.format(TestConfig.URL_ORDER_DETAIL + "/%d", 99999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        OrderDetail gotOrderDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(gotOrderDetail);
    }

    @Test
    public void testGetOrderDetail_NegativeId() throws IOException {
        // test get orderDetail
        String url = String.format(TestConfig.URL_ORDER_DETAIL + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        OrderDetail gotOrderDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(gotOrderDetail);
    }

    @Test(expected = IOException.class)
    public void testGetOrderDetail_NonNumericId() throws IOException {
        // test get orderDetail
        String url = String.format(TestConfig.URL_ORDER_DETAIL + "/%s", "nonnumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        OrderDetail gotOrderDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(gotOrderDetail);
    }

    @Test(expected = IOException.class)
    public void testGetOrderDetail_SpecialCharacterId() throws IOException {
        // test get orderDetail
        String url = String.format(TestConfig.URL_ORDER_DETAIL + "/%s", "*(*&*");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        OrderDetail gotOrderDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(gotOrderDetail);
    }

    @Test
    public void testGetAllOrderDetail() throws IOException {
        // add new orderDetail for testing
        Product product = TestUtil.addNewProductForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Order order = TestUtil.addNewOrderForTest(staff);
        OrderDetail addedOrderDetail = TestUtil.addNewOrderDetailForTest(order, product);

        // test get all orderDetails
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, "", "GET");
        List<OrderDetail> orderDetails = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<OrderDetail>>(){}.getType());
        Assert.assertTrue(orderDetails.size() >= 1);

        // delete unit test orderDetail after added
        TestUtil.deleteTestOrderDetail(addedOrderDetail);
        TestUtil.deleteTestOrder(order);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testCreateOrderDetail() throws IOException {
        // test add new orderDetail
        Staff staff = TestUtil.addNewStaffForTest();
        Order order = TestUtil.addNewOrderForTest(staff);
        String testData = String.format("{\n" +
                "\t\"order\": {\"id\": %d},\n" +
                "\t\"quantity\": 10,\n" +
                "\t\"price\": 123\n" +
                "}", order.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, testData, "POST");
        OrderDetail createdOrderDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertEquals(10, createdOrderDetail.getQuantity());

        // delete unit test orderDetail after added
        TestUtil.deleteTestOrderDetail(createdOrderDetail);
        TestUtil.deleteTestOrder(order);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testDeleteOrderDetail() throws IOException {
        // add new orderDetail for testing
        Product product = TestUtil.addNewProductForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Order order = TestUtil.addNewOrderForTest(staff);
        OrderDetail addedOrderDetail = TestUtil.addNewOrderDetailForTest(order, product);

        // test delete orderDetail
        String deleteData = String.format("{\"id\": %d}", addedOrderDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, deleteData, "DELETE");
        OrderDetail deletedOrderDetail = new Gson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertEquals(addedOrderDetail.getId(), deletedOrderDetail.getId());

        TestUtil.deleteTestOrder(order);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrderDetail_NonExistId() throws IOException {
        // test delete orderDetail
        String deleteData = String.format("{\"id\": %d}", 99999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, deleteData, "DELETE");
        OrderDetail deletedOrderDetail = new Gson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(deletedOrderDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrderDetail_NegativeId() throws IOException {
        // test delete orderDetail
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, deleteData, "DELETE");
        OrderDetail deletedOrderDetail = new Gson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(deletedOrderDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrderDetail_NonNumericId() throws IOException {
        // test delete orderDetail
        String deleteData = String.format("{\"id\": %s}", "nonnumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, deleteData, "DELETE");
        OrderDetail deletedOrderDetail = new Gson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(deletedOrderDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteOrderDetail_SpecialCharacterId() throws IOException {
        // test delete orderDetail
        String deleteData = String.format("{\"id\": %s}", "*()(**");
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, deleteData, "DELETE");
        OrderDetail deletedOrderDetail = new Gson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertNull(deletedOrderDetail);
    }

    @Test
    public void testUpdateOrderDetail() throws IOException {
        // add new orderDetail for testing
        Product product = TestUtil.addNewProductForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Order order = TestUtil.addNewOrderForTest(staff);
        OrderDetail addedOrderDetail = TestUtil.addNewOrderDetailForTest(order, product);

        // test update orderDetail
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"quantity\": 20,\n" +
                "    \"price\": 200\n" +
                "}", addedOrderDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, updateData, "PUT");
        OrderDetail updatedOrderDetail = new Gson().fromJson(json, new TypeToken<OrderDetail>(){}.getType());
        Assert.assertEquals(20, updatedOrderDetail.getQuantity());

        // delete unit test orderDetail after added
        TestUtil.deleteTestOrderDetail(addedOrderDetail);
        TestUtil.deleteTestOrder(order);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }
}

