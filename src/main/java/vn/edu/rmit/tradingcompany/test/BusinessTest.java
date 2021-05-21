package vn.edu.rmit.tradingcompany.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.edu.rmit.tradingcompany.model.*;
import org.junit.Assert;
import org.junit.Test;
import vn.edu.rmit.tradingcompany.util.*;

import java.io.IOException;
import java.util.HashMap;

public class BusinessTest {
    @Test
    public void testRevenue() throws IOException {
        String url = String.format("%s?from=%d&to=%d", TestConfig.URL_BUSINESS_REVENUE, 1599273149932L, 1599879149932L);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        HashMap<String, Float> jsonResponse = TestUtil.modifiedGson().fromJson(json, new TypeToken<HashMap<String, Float>>(){}.getType());
        Assert.assertEquals(0.0, jsonResponse.get("revenue"), 1);
    }

    @Test
    public void testInventory() throws IOException {
        long from = System.currentTimeMillis();
        Product product = TestUtil.addNewProductForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Customer customer = TestUtil.addNewCustomerForTest();
        ReceivingNote receivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        ReceivingNoteDetail receivingNoteDetail = TestUtil.addNewReceivingNoteDetailForTest(receivingNote, product);
        DeliveryNote deliveryNote = TestUtil.addNewDeliveryNoteForTest(customer);
        DeliveryNoteDetail deliveryNoteDetail = TestUtil.addNewDeliveryNoteDetailForTest(deliveryNote, product);
        Long to = System.currentTimeMillis();

        String url = String.format("%s?from=%d&to=%d", TestConfig.URL_BUSINESS_INVENTORY, from, to);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        HashMap<String, Object> jsonResponse = TestUtil.modifiedGson().fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
        Assert.assertEquals(ServiceUtil.timestampToDate(from), jsonResponse.get("from"));
        Assert.assertEquals(ServiceUtil.timestampToDate(to), jsonResponse.get("to"));

        TestUtil.deleteTestDeliveryNoteDetail(deliveryNoteDetail);
        TestUtil.deleteTestDeliveryNote(deliveryNote);
        TestUtil.deleteTestReceivingNoteDetail(receivingNoteDetail);
        TestUtil.deleteTestReceivingNote(receivingNote);
        TestUtil.deleteTestCustomer(customer);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }
}
