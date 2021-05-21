package vn.edu.rmit.tradingcompany.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.service.ProductService;
import vn.edu.rmit.tradingcompany.test.TestConfig;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TestUtil {
    public static String getHttpResponse(String path, String requestBody, String requestMethod) throws IOException {
        InputStreamReader inputStreamReader = sendHttpRequest(path, requestBody, requestMethod);

        // read response
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String buffer = "";
        StringBuilder response = new StringBuilder();
        while((buffer = bufferedReader.readLine()) != null){
            response.append(buffer);
        }

        return response.toString();
    }

    public static InputStreamReader sendHttpRequest(String path, String requestBody, String requestMethod) throws IOException {
        // construct new HTTP connection
        URL url = new URL(path);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(requestMethod);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");

        // add request body
        if (!requestMethod.equals("GET")) {
            byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
            try (DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream())) {
                dataOutputStream.write(requestBodyBytes);
            }
        }

        return new InputStreamReader(httpURLConnection.getInputStream());
    }

    public static Gson modifiedGson() {
        // reference: https://stackoverflow.com/questions/6873020/gson-date-format
        // author: M.L (https://stackoverflow.com/users/439945/m-l)

        GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        return builder.create();
    }

    public static Product addNewProductForTest() throws IOException {
        String testData = "{\n" +
                "    \"name\": \"unit test product\",\n" +
                "    \"brand\": \"test brand\",\n" +
                "    \"company\": \"test company\",\n" +
                "    \"description\": \"test description\",\n" +
                "    \"model\": \"test model\",\n" +
                "    \"price\": 123\n" +
                "}";
        String j = TestUtil.getHttpResponse(TestConfig.URL_PRODUCT, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<Product>(){}.getType());
    }

    public static void deleteTestProduct(Product product) throws IOException {
        String deleteData = String.format("{\"id\": %d}", product.getId());
        TestUtil.getHttpResponse(TestConfig.URL_PRODUCT, deleteData, "DELETE");
    }

    public static Customer addNewCustomerForTest() throws IOException {
        String randomInt = String.valueOf(Math.floor(Math.random()*(999999)));

        String testData = "{\n" +
                "    \"name\": \"unit test customer\",\n" +
                "    \"address\": \"rmit\",\n" +
                "    \"phone\": \"0123456789\",\n" +
                "    \"fax\": \"9876543210\",\n" +
                "    \"email\": \"" + randomInt + "@rmit.edu.vn\",\n" +
                "    \"contactPerson\": \"huan\"\n" +
                "}";
        String j = TestUtil.getHttpResponse(TestConfig.URL_CUSTOMER, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<Customer>(){}.getType());
    }

    public static void deleteTestCustomer(Customer customer) throws IOException {
        String deleteData = String.format("{\"id\": %d}", customer.getId());
        TestUtil.getHttpResponse(TestConfig.URL_CUSTOMER, deleteData, "DELETE");
    }

    public static Staff addNewStaffForTest() throws IOException {
        String randomInt = String.valueOf(Math.floor(Math.random()*(999999)));

        String testData = "{\n" +
                "    \"name\": \"unit test staff\",\n" +
                "    \"address\": \"localhost\",\n" +
                "    \"phone\": \"0123456789\",\n" +
                "    \"email\": \"" + randomInt + "@rmit.edu.vn\"\n" +
                "}";
        String j = TestUtil.getHttpResponse(TestConfig.URL_STAFF, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<Staff>(){}.getType());
    }

    public static void deleteTestStaff(Staff staff) throws IOException {
        String deleteData = String.format("{\"id\": %d}", staff.getId());
        TestUtil.getHttpResponse(TestConfig.URL_STAFF, deleteData, "DELETE");
    }

    public static Invoice addNewInvoiceForTest(Customer customer, Staff staff) throws IOException {
        String testData = String.format("{\n" +
                "\t\"customer\": {\"id\": %s},\n" +
                "\t\"staff\": {\"id\": %s},\n" +
                "\t\"totalValue\": 123\n" +
                "}", customer.getId(), staff.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, testData, "POST");
        return TestUtil.modifiedGson().fromJson(j, new TypeToken<Invoice>(){}.getType());
    }

    public static void deleteTestInvoice(Invoice invoice) throws IOException {
        String deleteData = String.format("{\"id\": %d}", invoice.getId());
        TestUtil.getHttpResponse(TestConfig.URL_INVOICE, deleteData, "DELETE");
    }

    public static InvoiceDetail addNewInvoiceDetailForTest(Invoice invoice, Product product) throws IOException {
        String testData = String.format("{ \n" +
                "\t\"invoice\": {\"id\": %d},\n" +
                "\t\"quantity\": 1,\n" +
                "\t\"product\": {\"id\": %d}\n" +
                "}", invoice.getId(), product.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<InvoiceDetail>(){}.getType());
    }

    public static void deleteTestInvoiceDetail(InvoiceDetail invoiceDetail) throws IOException {
        String deleteData = String.format("{\"id\": %d}", invoiceDetail.getId());
        TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, deleteData, "DELETE");
    }

    public static DeliveryNote addNewDeliveryNoteForTest(Customer customer) throws IOException {
        String testData = String.format("{\n" +
                "\t\"customer\": {\"id\": %d}\n" +
                "}", customer.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, testData, "POST");
        return TestUtil.modifiedGson().fromJson(j, new TypeToken<DeliveryNote>(){}.getType());
    }

    public static void deleteTestDeliveryNote(DeliveryNote deliveryNote) throws IOException {
        String deleteData = String.format("{\"id\": %d}", deliveryNote.getId());
        TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, deleteData, "DELETE");
    }
    
    public static DeliveryNoteDetail addNewDeliveryNoteDetailForTest(DeliveryNote deliveryNote, Product product) throws IOException {
        String testData = String.format("{ \n" +
                "\t\"deliveryNote\": {\"id\": %d},\n" +
                "\t\"product\": {\"id\": %d},\n" +
                "\t\"quantity\": 1\n" +
                "}", deliveryNote.getId(), product.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<DeliveryNoteDetail>(){}.getType());
    }

    public static void deleteTestDeliveryNoteDetail(DeliveryNoteDetail deliveryNoteDetail) throws IOException {
        String deleteData = String.format("{\"id\": %d}", deliveryNoteDetail.getId());
        TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, deleteData, "DELETE");
    }

    public static ReceivingNote addNewReceivingNoteForTest(Staff staff) throws IOException {
        String testData = String.format("{\n" +
                "\t\"staff\": {\"id\": %d}\n" +
                "}", staff.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, testData, "POST");
        return TestUtil.modifiedGson().fromJson(j, new TypeToken<ReceivingNote>(){}.getType());
    }

    public static void deleteTestReceivingNote(ReceivingNote receivingNote) throws IOException {
        String deleteData = String.format("{\"id\": %d}", receivingNote.getId());
        TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, deleteData, "DELETE");
    }

    public static ReceivingNoteDetail addNewReceivingNoteDetailForTest(ReceivingNote receivingNote, Product product) throws IOException {
        String testData = String.format("{ \n" +
                "\t\"receivingNote\": {\"id\": %d},\n" +
                "\t\"product\": {\"id\": %d},\n" +
                "\t\"quantity\": 1\n" +
                "}", receivingNote.getId(), product.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<ReceivingNoteDetail>(){}.getType());
    }

    public static void deleteTestReceivingNoteDetail(ReceivingNoteDetail receivingNoteDetail) throws IOException {
        String deleteData = String.format("{\"id\": %d}", receivingNoteDetail.getId());
        TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, deleteData, "DELETE");
    }

    public static Order addNewOrderForTest(Staff staff) throws IOException {
        String testData = String.format("{\n" +
                "\t\"staff\": {\"id\": %d}\n" +
                "}", staff.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_ORDER, testData, "POST");
        return TestUtil.modifiedGson().fromJson(j, new TypeToken<Order>(){}.getType());
    }

    public static void deleteTestOrder(Order order) throws IOException {
        String deleteData = String.format("{\"id\": %d}", order.getId());
        TestUtil.getHttpResponse(TestConfig.URL_ORDER, deleteData, "DELETE");
    }

    public static OrderDetail addNewOrderDetailForTest(Order order, Product product) throws IOException {
        String testData = String.format("{ \n" +
                "\t\"order\": {\"id\": %d},\n" +
                "\t\"product\": {\"id\": %d},\n" +
                "\t\"quantity\": 1,\n" +
                "\t\"price\": 123\n" +
                "}", order.getId(), product.getId());
        String j = TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, testData, "POST");
        return new Gson().fromJson(j, new TypeToken<OrderDetail>(){}.getType());
    }

    public static void deleteTestOrderDetail(OrderDetail orderDetail) throws IOException {
        String deleteData = String.format("{\"id\": %d}", orderDetail.getId());
        TestUtil.getHttpResponse(TestConfig.URL_ORDER_DETAIL, deleteData, "DELETE");
    }
}
