package vn.edu.rmit.tradingcompany.test;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import vn.edu.rmit.tradingcompany.model.*;
import org.junit.Assert;
import org.junit.Test;
import vn.edu.rmit.tradingcompany.util.TestUtil;

import java.io.IOException;
import java.util.List;

public class DeliveryNoteTests {
    @Test
    public void testGetDeliveryNote() throws IOException {
        Customer customer = TestUtil.addNewCustomerForTest();
        // add new deliveryNote for testing
        DeliveryNote addedDeliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);

        // test get deliveryNote
        String url = String.format(TestConfig.URL_DELIVERY_NOTE + "/%d", addedDeliveryNote.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNote gotDeliveryNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertEquals(addedDeliveryNote.getId(), gotDeliveryNote.getId());
        Assert.assertEquals(addedDeliveryNote.getCustomer().getId(), gotDeliveryNote.getCustomer().getId());

        // delete unit test deliveryNote after added
        TestUtil.deleteTestDeliveryNote(addedDeliveryNote);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testGetDeliveryNote_NonExistId() throws IOException {
        // test get deliveryNote
        String url = String.format(TestConfig.URL_DELIVERY_NOTE + "/%d", 999999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNote gotDeliveryNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(gotDeliveryNote);
    }

    @Test
    public void testGetDeliveryNote_NegativeId() throws IOException {
        // test get deliveryNote
        String url = String.format(TestConfig.URL_DELIVERY_NOTE + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNote gotDeliveryNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(gotDeliveryNote);
    }

    @Test(expected = IOException.class)
    public void testGetDeliveryNote_NonNumericId() throws IOException {
        // test get deliveryNote
        String url = String.format(TestConfig.URL_DELIVERY_NOTE + "/%s", "nonnumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNote gotDeliveryNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(gotDeliveryNote);
    }

    @Test(expected = IOException.class)
    public void testGetDeliveryNote_SpecialCharacterId() throws IOException {
        // test get deliveryNote
        String url = String.format(TestConfig.URL_DELIVERY_NOTE + "/%s", ")(*&^%");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNote gotDeliveryNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(gotDeliveryNote);
    }

    @Test
    public void testGetAllDeliveryNote() throws IOException {
        Customer customer = TestUtil.addNewCustomerForTest();
        // add new deliveryNote for testing
        DeliveryNote addedDeliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);

        // test get all deliveryNotes
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, "", "GET");
        List<DeliveryNote> deliveryNotes = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<DeliveryNote>>(){}.getType());
        Assert.assertTrue(deliveryNotes.size() >= 1);

        // delete unit test deliveryNote after added
        TestUtil.deleteTestDeliveryNote(addedDeliveryNote);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testCreateDeliveryNote() throws IOException {
        // test add new deliveryNote
        Customer customer = TestUtil.addNewCustomerForTest();
        String testData = String.format("{\n" +
                "\t\"customer\": {\"id\": %d}\n" +
                "}", customer.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, testData, "POST");
        DeliveryNote createdDeliveryNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertEquals(customer.getId(), createdDeliveryNote.getCustomer().getId());

        // delete unit test deliveryNote after added
        TestUtil.deleteTestDeliveryNote(createdDeliveryNote);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testDeleteDeliveryNote() throws IOException {
        Customer customer = TestUtil.addNewCustomerForTest();
        // add new deliveryNote for testing
        DeliveryNote addedDeliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);

        // test delete deliveryNote
        String deleteData = String.format("{\"id\": %d}", addedDeliveryNote.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, deleteData, "DELETE");
        DeliveryNote deletedDeliveryNote = new Gson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertEquals(addedDeliveryNote.getId(), deletedDeliveryNote.getId());

        TestUtil.deleteTestCustomer(customer);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNote_NonExistId() throws IOException {
        // test delete deliveryNote
        String deleteData = String.format("{\"id\": %d}", 99999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, deleteData, "DELETE");
        DeliveryNote deletedDeliveryNote = new Gson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(deletedDeliveryNote);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNote_NegativeId() throws IOException {
        // test delete deliveryNote
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, deleteData, "DELETE");
        DeliveryNote deletedDeliveryNote = new Gson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(deletedDeliveryNote);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNote_NonNumericId() throws IOException {
        // test delete deliveryNote
        String deleteData = String.format("{\"id\": %s}", "nonumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, deleteData, "DELETE");
        DeliveryNote deletedDeliveryNote = new Gson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(deletedDeliveryNote);
    }


    @Test(expected = IOException.class)
    public void testDeleteDeliveryNote_SpecialCharacterId() throws IOException {
        // test delete deliveryNote
        String deleteData = String.format("{\"id\": %s}", "*&^%$");
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, deleteData, "DELETE");
        DeliveryNote deletedDeliveryNote = new Gson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertNull(deletedDeliveryNote);
    }

    @Test
    public void testUpdateDeliveryNote() throws IOException {
        Customer customer = TestUtil.addNewCustomerForTest();
        // add new deliveryNote for testing
        DeliveryNote addedDeliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        Customer newCustomer = TestUtil.addNewCustomerForTest();

        // test update deliveryNote
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"customer\": {\n" +
                "        \"id\": %d\n" +
                "    }\n" +
                "}", addedDeliveryNote.getId(), newCustomer.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE, updateData, "PUT");
        DeliveryNote updatedDeliveryNote = new Gson().fromJson(json, new TypeToken<DeliveryNote>(){}.getType());
        Assert.assertEquals(newCustomer.getId(), updatedDeliveryNote.getCustomer().getId());

        // delete unit test deliveryNote after added
        TestUtil.deleteTestDeliveryNote(addedDeliveryNote);
        TestUtil.deleteTestCustomer(newCustomer);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testGetDeliveryNoteDetail() throws IOException {
        // add new deliveryNoteDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Product product = TestUtil.addNewProductForTest();
        DeliveryNote deliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        DeliveryNoteDetail addedDeliveryNoteDetail = TestUtil.addNewDeliveryNoteDetailForTest(deliveryNote, product);

        // test get deliveryNoteDetail
        String url = String.format(TestConfig.URL_DELIVERY_NOTE_DETAIL + "/%d", addedDeliveryNoteDetail.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNoteDetail gotDeliveryNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertEquals(addedDeliveryNoteDetail.getId(), gotDeliveryNoteDetail.getId());

        // delete unit test deliveryNoteDetail after added
        TestUtil.deleteTestDeliveryNoteDetail(addedDeliveryNoteDetail);
        TestUtil.deleteTestDeliveryNote(deliveryNote);
        TestUtil.deleteTestCustomer(customer);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testGetDeliveryNoteDetail_NonExistId() throws IOException {
        // test get deliveryNoteDetail
        String url = String.format(TestConfig.URL_DELIVERY_NOTE_DETAIL + "/%d", 999999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNoteDetail gotDeliveryNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(gotDeliveryNoteDetail);
    }

    @Test
    public void testGetDeliveryNoteDetail_NegativeId() throws IOException {
        // test get deliveryNoteDetail
        String url = String.format(TestConfig.URL_DELIVERY_NOTE_DETAIL + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNoteDetail gotDeliveryNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(gotDeliveryNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testGetDeliveryNoteDetail_NonNumericId() throws IOException {
        // test get deliveryNoteDetail
        String url = String.format(TestConfig.URL_DELIVERY_NOTE_DETAIL + "/%s", "nonnumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNoteDetail gotDeliveryNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(gotDeliveryNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testGetDeliveryNoteDetail_SpecialCharacterId() throws IOException {
        // test get deliveryNoteDetail
        String url = String.format(TestConfig.URL_DELIVERY_NOTE_DETAIL + "/%s", "*&^%");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        DeliveryNoteDetail gotDeliveryNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(gotDeliveryNoteDetail);
    }


    @Test
    public void testGetAllDeliveryNoteDetail() throws IOException {
        // add new deliveryNoteDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Product product = TestUtil.addNewProductForTest();
        DeliveryNote deliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        DeliveryNoteDetail addedDeliveryNoteDetail = TestUtil.addNewDeliveryNoteDetailForTest(deliveryNote, product);

        // test get all deliveryNoteDetails
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, "", "GET");
        List<DeliveryNoteDetail> deliveryNoteDetails = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<DeliveryNoteDetail>>(){}.getType());
        Assert.assertTrue(deliveryNoteDetails.size() >= 1);

        // delete unit test deliveryNoteDetail after added
        TestUtil.deleteTestDeliveryNoteDetail(addedDeliveryNoteDetail);
        TestUtil.deleteTestDeliveryNote(deliveryNote);
        TestUtil.deleteTestCustomer(customer);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testCreateDeliveryNoteDetail() throws IOException {
        // test add new deliveryNoteDetail
        Customer customer = TestUtil.addNewCustomerForTest();
        Product product = TestUtil.addNewProductForTest();
        DeliveryNote deliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        String testData = String.format("{\n" +
                "\t\"deliveryNote\": {\"id\": %d},\n" +
                "\t\"product\": {\"id\": %d},\n" +
                "\t\"quantity\": 10\n" +
                "}", deliveryNote.getId(), product.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, testData, "POST");
        DeliveryNoteDetail createdDeliveryNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertEquals(10, createdDeliveryNoteDetail.getQuantity());

        // delete unit test deliveryNoteDetail after added
        TestUtil.deleteTestDeliveryNoteDetail(createdDeliveryNoteDetail);
        TestUtil.deleteTestDeliveryNote(deliveryNote);
        TestUtil.deleteTestCustomer(customer);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testDeleteDeliveryNoteDetail() throws IOException {
        // add new deliveryNoteDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Product product = TestUtil.addNewProductForTest();
        DeliveryNote deliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        DeliveryNoteDetail addedDeliveryNoteDetail = TestUtil.addNewDeliveryNoteDetailForTest(deliveryNote, product);

        // test delete deliveryNoteDetail
        String deleteData = String.format("{\"id\": %d}", addedDeliveryNoteDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, deleteData, "DELETE");
        DeliveryNoteDetail deletedDeliveryNoteDetail = new Gson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertEquals(addedDeliveryNoteDetail.getId(), deletedDeliveryNoteDetail.getId());

        TestUtil.deleteTestDeliveryNote(deliveryNote);
        TestUtil.deleteTestCustomer(customer);
        TestUtil.deleteTestProduct(product);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNoteDetail_NonExistId() throws IOException {
        // test delete deliveryNoteDetail
        String deleteData = String.format("{\"id\": %d}", 999999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, deleteData, "DELETE");
        DeliveryNoteDetail deletedDeliveryNoteDetail = new Gson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(deletedDeliveryNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNoteDetail_NegativeId() throws IOException {
        // test delete deliveryNoteDetail
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, deleteData, "DELETE");
        DeliveryNoteDetail deletedDeliveryNoteDetail = new Gson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(deletedDeliveryNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNoteDetail_NonNumericId() throws IOException {
        // test delete deliveryNoteDetail
        String deleteData = String.format("{\"id\": %s}", "nonnumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, deleteData, "DELETE");
        DeliveryNoteDetail deletedDeliveryNoteDetail = new Gson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(deletedDeliveryNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteDeliveryNoteDetail_SpecialCharacterId() throws IOException {
        // test delete deliveryNoteDetail
        String deleteData = String.format("{\"id\": %s}", "*&^%%^");
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, deleteData, "DELETE");
        DeliveryNoteDetail deletedDeliveryNoteDetail = new Gson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertNull(deletedDeliveryNoteDetail);
    }

    @Test
    public void testUpdateDeliveryNoteDetail() throws IOException {
        // add new deliveryNoteDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Product product = TestUtil.addNewProductForTest();
        DeliveryNote deliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        DeliveryNoteDetail addedDeliveryNoteDetail = TestUtil.addNewDeliveryNoteDetailForTest(deliveryNote, product);

        // test update deliveryNoteDetail
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"quantity\": 20\n" +
                "}", addedDeliveryNoteDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_DELIVERY_NOTE_DETAIL, updateData, "PUT");
        DeliveryNoteDetail updatedDeliveryNoteDetail = new Gson().fromJson(json, new TypeToken<DeliveryNoteDetail>(){}.getType());
        Assert.assertEquals(20, updatedDeliveryNoteDetail.getQuantity());

        // delete unit test deliveryNoteDetail after added
        TestUtil.deleteTestDeliveryNoteDetail(addedDeliveryNoteDetail);
        TestUtil.deleteTestDeliveryNote(deliveryNote);
        TestUtil.deleteTestCustomer(customer);
        TestUtil.deleteTestProduct(product);
    }
}

