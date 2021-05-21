package vn.edu.rmit.tradingcompany.test;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import vn.edu.rmit.tradingcompany.model.*;
import org.junit.Assert;
import org.junit.Test;
import vn.edu.rmit.tradingcompany.util.TestUtil;

import java.io.IOException;
import java.util.List;

public class InvoiceTests {
    @Test
    public void testGetInvoice() throws IOException {
        Staff addedStaff = TestUtil.addNewStaffForTest();
        Customer addedCustomer = TestUtil.addNewCustomerForTest();
        // add new invoice for testing
        Invoice addedInvoice = TestUtil.addNewInvoiceForTest(addedCustomer, addedStaff);

        // test get invoice
        String url = String.format(TestConfig.URL_INVOICE + "/%d", addedInvoice.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Invoice gotInvoice = TestUtil.modifiedGson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertEquals(addedInvoice.getId(), gotInvoice.getId());
        Assert.assertEquals(addedInvoice.getCustomer().getId(), gotInvoice.getCustomer().getId());

        // delete unit test invoice after added
        TestUtil.deleteTestInvoice(addedInvoice);
        TestUtil.deleteTestStaff(addedStaff);
        TestUtil.deleteTestCustomer(addedCustomer);
    }

    @Test
    public void testGetInvoice_NonExistId() throws IOException {
        // test get invoice
        String url = String.format(TestConfig.URL_INVOICE + "/%d", 99999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Invoice gotInvoice = TestUtil.modifiedGson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(gotInvoice);
    }

    @Test
    public void testGetInvoice_NegativeId() throws IOException {
        // test get invoice
        String url = String.format(TestConfig.URL_INVOICE + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Invoice gotInvoice = TestUtil.modifiedGson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(gotInvoice);
    }

    @Test(expected = IOException.class)
    public void testGetInvoice_NonNumericId() throws IOException {
        // test get invoice
        String url = String.format(TestConfig.URL_INVOICE + "/%s", "nonumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Invoice gotInvoice = TestUtil.modifiedGson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(gotInvoice);
    }

    @Test(expected = IOException.class)
    public void testGetInvoice_SpecialCharacterId() throws IOException {
        // test get invoice
        String url = String.format(TestConfig.URL_INVOICE + "/%s", "*&^&");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        Invoice gotInvoice = TestUtil.modifiedGson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(gotInvoice);
    }

    @Test
    public void testGetAllInvoice() throws IOException {
        Staff addedStaff = TestUtil.addNewStaffForTest();
        Customer addedCustomer = TestUtil.addNewCustomerForTest();
        // add new invoice for testing
        Invoice addedInvoice = TestUtil.addNewInvoiceForTest(addedCustomer, addedStaff);

        // test get all invoices
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, "", "GET");
        List<Invoice> invoices = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<Invoice>>(){}.getType());
        Assert.assertTrue(invoices.size() >= 1);

        // delete unit test invoice after added
        TestUtil.deleteTestInvoice(addedInvoice);
        TestUtil.deleteTestStaff(addedStaff);
        TestUtil.deleteTestCustomer(addedCustomer);
    }

    @Test
    public void testCreateInvoice() throws IOException {
        // test add new invoice
        Customer customer = TestUtil.addNewCustomerForTest();
        String testData = String.format("{\n" +
                "\t\"customer\": {\"id\": %d}\n" +
                "}", customer.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, testData, "POST");
        Invoice createdInvoice = TestUtil.modifiedGson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertEquals(customer.getId(), createdInvoice.getCustomer().getId());

        // delete unit test invoice after added
        TestUtil.deleteTestInvoice(createdInvoice);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testDeleteInvoice() throws IOException {
        Staff addedStaff = TestUtil.addNewStaffForTest();
        Customer addedCustomer = TestUtil.addNewCustomerForTest();
        // add new invoice for testing
        Invoice addedInvoice = TestUtil.addNewInvoiceForTest(addedCustomer, addedStaff);

        // test delete invoice
        String deleteData = String.format("{\"id\": %d}", addedInvoice.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, deleteData, "DELETE");
        Invoice deletedInvoice = new Gson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertEquals(addedInvoice.getId(), deletedInvoice.getId());

        TestUtil.deleteTestStaff(addedStaff);
        TestUtil.deleteTestCustomer(addedCustomer);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoice_NonExistId() throws IOException {
        // test delete invoice
        String deleteData = String.format("{\"id\": %d}", 999999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, deleteData, "DELETE");
        Invoice deletedInvoice = new Gson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(deletedInvoice);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoice_NegativeId() throws IOException {
        // test delete invoice
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, deleteData, "DELETE");
        Invoice deletedInvoice = new Gson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(deletedInvoice);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoice_NonNumericId() throws IOException {
        // test delete invoice
        String deleteData = String.format("{\"id\": %s}", "nonnumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, deleteData, "DELETE");
        Invoice deletedInvoice = new Gson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(deletedInvoice);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoice_SpecialCharacterId() throws IOException {
        // test delete invoice
        String deleteData = String.format("{\"id\": %s}", "*((*&");
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, deleteData, "DELETE");
        Invoice deletedInvoice = new Gson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertNull(deletedInvoice);
    }

    @Test
    public void testUpdateInvoice() throws IOException {
        Staff addedStaff = TestUtil.addNewStaffForTest();
        Customer addedCustomer = TestUtil.addNewCustomerForTest();
        // add new invoice for testing
        Invoice addedInvoice = TestUtil.addNewInvoiceForTest(addedCustomer, addedStaff);
        Customer newCustomer = TestUtil.addNewCustomerForTest();

        // test update invoice
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"customer\": {\n" +
                "        \"id\": %d\n" +
                "    }\n" +
                "}", addedInvoice.getId(), newCustomer.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE, updateData, "PUT");
        Invoice updatedInvoice = new Gson().fromJson(json, new TypeToken<Invoice>(){}.getType());
        Assert.assertEquals(newCustomer.getId(), updatedInvoice.getCustomer().getId());

        // delete unit test invoice after added
        TestUtil.deleteTestInvoice(addedInvoice);
        TestUtil.deleteTestCustomer(newCustomer);
        TestUtil.deleteTestStaff(addedStaff);
        TestUtil.deleteTestCustomer(addedCustomer);
    }

    @Test
    public void testGetInvoiceDetail() throws IOException {
        // add new invoiceDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Invoice invoice = TestUtil.addNewInvoiceForTest(customer, staff);
        Product product = TestUtil.addNewProductForTest();
        InvoiceDetail addedInvoiceDetail = TestUtil.addNewInvoiceDetailForTest(invoice, product);

        // test get invoiceDetail
        String url = String.format(TestConfig.URL_INVOICE_DETAIL + "/%d", addedInvoiceDetail.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        InvoiceDetail gotInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertEquals(addedInvoiceDetail.getId(), gotInvoiceDetail.getId());

        // delete unit test invoiceDetail after added
        TestUtil.deleteTestInvoiceDetail(addedInvoiceDetail);
        TestUtil.deleteTestProduct(product);
        TestUtil.deleteTestInvoice(invoice);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testGetInvoiceDetail_NonExistId() throws IOException {
        // test get invoiceDetail
        String url = String.format(TestConfig.URL_INVOICE_DETAIL + "/%d", 99999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        InvoiceDetail gotInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(gotInvoiceDetail);
    }

    @Test
    public void testGetInvoiceDetail_NegativeId() throws IOException {
        // test get invoiceDetail
        String url = String.format(TestConfig.URL_INVOICE_DETAIL + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        InvoiceDetail gotInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(gotInvoiceDetail);
    }

    @Test(expected = IOException.class)
    public void testGetInvoiceDetail_NonNumericId() throws IOException {
        // test get invoiceDetail
        String url = String.format(TestConfig.URL_INVOICE_DETAIL + "/%s", "nonnumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        InvoiceDetail gotInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(gotInvoiceDetail);
    }

    @Test(expected = IOException.class)
    public void testGetInvoiceDetail_SpecialCharacterId() throws IOException {
        // test get invoiceDetail
        String url = String.format(TestConfig.URL_INVOICE_DETAIL + "/%s", "*^&*");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        InvoiceDetail gotInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(gotInvoiceDetail);
    }

    @Test
    public void testGetAllInvoiceDetail() throws IOException {
        // add new invoiceDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Invoice invoice = TestUtil.addNewInvoiceForTest(customer, staff);
        Product product = TestUtil.addNewProductForTest();
        InvoiceDetail addedInvoiceDetail = TestUtil.addNewInvoiceDetailForTest(invoice, product);

        // test get all invoiceDetails
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, "", "GET");
        List<InvoiceDetail> invoiceDetails = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<InvoiceDetail>>(){}.getType());
        Assert.assertTrue(invoiceDetails.size() >= 1);

        // delete unit test invoiceDetail after added
        TestUtil.deleteTestInvoiceDetail(addedInvoiceDetail);
        TestUtil.deleteTestProduct(product);
        TestUtil.deleteTestInvoice(invoice);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test
    public void testCreateInvoiceDetail() throws IOException {
        Staff addedStaff = TestUtil.addNewStaffForTest();
        Customer addedCustomer = TestUtil.addNewCustomerForTest();
        // test add new invoiceDetail
        Invoice invoice = TestUtil.addNewInvoiceForTest(addedCustomer, addedStaff);
        String testData = String.format("{\n" +
                "\t\"invoice\": {\"id\": %d},\n" +
                "\t\"quantity\": 10\n" +
                "}", invoice.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, testData, "POST");
        InvoiceDetail createdInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertEquals(10, createdInvoiceDetail.getQuantity());

        // delete unit test invoiceDetail after added
        TestUtil.deleteTestInvoiceDetail(createdInvoiceDetail);
        TestUtil.deleteTestInvoice(invoice);
        TestUtil.deleteTestStaff(addedStaff);
        TestUtil.deleteTestCustomer(addedCustomer);
    }

    @Test
    public void testCreateInvoiceDetail_NonNumericQuantity() throws IOException {
        Staff addedStaff = TestUtil.addNewStaffForTest();
        Customer addedCustomer = TestUtil.addNewCustomerForTest();
        // test add new invoiceDetail
        Invoice invoice = TestUtil.addNewInvoiceForTest(addedCustomer, addedStaff);
        String testData = String.format("{\n" +
                "\t\"invoice\": {\"id\": %d},\n" +
                "\t\"quantity\": \"nonnumeric\"\n" +
                "}", invoice.getId());
        String json = "";
        try {
            json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, testData, "POST");
        } catch (IOException e) {
            InvoiceDetail createdInvoiceDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
            Assert.assertNull(createdInvoiceDetail);
        }

        // delete unit test invoiceDetail after added
        TestUtil.deleteTestInvoice(invoice);
        TestUtil.deleteTestStaff(addedStaff);
        TestUtil.deleteTestCustomer(addedCustomer);
    }


    @Test
    public void testDeleteInvoiceDetail() throws IOException {
        // add new invoiceDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Invoice invoice = TestUtil.addNewInvoiceForTest(customer, staff);
        Product product = TestUtil.addNewProductForTest();
        InvoiceDetail addedInvoiceDetail = TestUtil.addNewInvoiceDetailForTest(invoice, product);

        // test delete invoiceDetail
        String deleteData = String.format("{\"id\": %d}", addedInvoiceDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, deleteData, "DELETE");
        InvoiceDetail deletedInvoiceDetail = new Gson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertEquals(addedInvoiceDetail.getId(), deletedInvoiceDetail.getId());

        TestUtil.deleteTestProduct(product);
        TestUtil.deleteTestInvoice(invoice);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestCustomer(customer);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoiceDetail_NonExistId() throws IOException {
        // test delete invoiceDetail
        String deleteData = String.format("{\"id\": %d}", 99999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, deleteData, "DELETE");
        InvoiceDetail deletedInvoiceDetail = new Gson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(deletedInvoiceDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoiceDetail_NegativeId() throws IOException {
        // test delete invoiceDetail
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, deleteData, "DELETE");
        InvoiceDetail deletedInvoiceDetail = new Gson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(deletedInvoiceDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoiceDetail_NonNumericId() throws IOException {
        // test delete invoiceDetail
        String deleteData = String.format("{\"id\": %s}", "nonnumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, deleteData, "DELETE");
        InvoiceDetail deletedInvoiceDetail = new Gson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(deletedInvoiceDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteInvoiceDetail_SpecialCharacterId() throws IOException {
        // test delete invoiceDetail
        String deleteData = String.format("{\"id\": %s}", "*(&*(");
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, deleteData, "DELETE");
        InvoiceDetail deletedInvoiceDetail = new Gson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertNull(deletedInvoiceDetail);
    }

    @Test
    public void testUpdateInvoiceDetail() throws IOException {
        // add new invoiceDetail for testing
        Customer customer = TestUtil.addNewCustomerForTest();
        Staff staff = TestUtil.addNewStaffForTest();
        Invoice invoice = TestUtil.addNewInvoiceForTest(customer, staff);
        Product product = TestUtil.addNewProductForTest();
        InvoiceDetail addedInvoiceDetail = TestUtil.addNewInvoiceDetailForTest(invoice, product);

        // test update invoiceDetail
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"quantity\": 20\n" +
                "}", addedInvoiceDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_INVOICE_DETAIL, updateData, "PUT");
        InvoiceDetail updatedInvoiceDetail = new Gson().fromJson(json, new TypeToken<InvoiceDetail>(){}.getType());
        Assert.assertEquals(20, updatedInvoiceDetail.getQuantity());

        // delete unit test invoiceDetail after added
        TestUtil.deleteTestInvoiceDetail(addedInvoiceDetail);
        TestUtil.deleteTestProduct(product);
        TestUtil.deleteTestInvoice(invoice);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestCustomer(customer);
    }
}

