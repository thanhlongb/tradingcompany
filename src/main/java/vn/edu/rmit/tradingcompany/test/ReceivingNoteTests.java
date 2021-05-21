package vn.edu.rmit.tradingcompany.test;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import vn.edu.rmit.tradingcompany.model.*;
import org.junit.Assert;
import org.junit.Test;
import vn.edu.rmit.tradingcompany.util.TestUtil;

import java.io.IOException;
import java.util.List;

public class ReceivingNoteTests {
    @Test
    public void testGetReceivingNote() throws IOException {
        // add new receivingNote for testing
        Staff staff = TestUtil.addNewStaffForTest();
        ReceivingNote addedReceivingNote = TestUtil.addNewReceivingNoteForTest(staff);

        // test get receivingNote
        String url = String.format(TestConfig.URL_RECEIVING_NOTE + "/%d", addedReceivingNote.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNote gotReceivingNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertEquals(addedReceivingNote.getId(), gotReceivingNote.getId());

        // delete unit test receivingNote after added
        TestUtil.deleteTestReceivingNote(addedReceivingNote);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testGetReceivingNote_NonExistId() throws IOException {
        // test get receivingNote
        String url = String.format(TestConfig.URL_RECEIVING_NOTE + "/%d", 99999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNote gotReceivingNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(gotReceivingNote);
    }

    @Test
    public void testGetReceivingNote_NegativeId() throws IOException {
        // test get receivingNote
        String url = String.format(TestConfig.URL_RECEIVING_NOTE + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNote gotReceivingNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(gotReceivingNote);
    }

    @Test(expected = IOException.class)
    public void testGetReceivingNote_NonNumericId() throws IOException {
        // test get receivingNote
        String url = String.format(TestConfig.URL_RECEIVING_NOTE + "/%s", "nonnumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNote gotReceivingNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(gotReceivingNote);
    }

    @Test(expected = IOException.class)
    public void testGetReceivingNote_SpecialCharacterId() throws IOException {
        // test get receivingNote
        String url = String.format(TestConfig.URL_RECEIVING_NOTE + "/%s", "*((*&");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNote gotReceivingNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(gotReceivingNote);
    }

    @Test
    public void testGetAllReceivingNote() throws IOException {
        // add new receivingNote for testing
        Staff staff = TestUtil.addNewStaffForTest();
        ReceivingNote addedReceivingNote = TestUtil.addNewReceivingNoteForTest(staff);

        // test get all receivingNotes
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, "", "GET");
        List<ReceivingNote> receivingNotes = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<ReceivingNote>>(){}.getType());
        Assert.assertTrue(receivingNotes.size() >= 1);

        // delete unit test receivingNote after added
        TestUtil.deleteTestReceivingNote(addedReceivingNote);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testCreateReceivingNote() throws IOException {
        // test add new receivingNote
        Staff staff = TestUtil.addNewStaffForTest();
        String testData = String.format("{\n" +
                "\t\"staff\": {\"id\": %d}\n" +
                "}", staff.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, testData, "POST");
        ReceivingNote createdReceivingNote = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertEquals(staff.getId(), createdReceivingNote.getStaff().getId());

        // delete unit test receivingNote after added
        TestUtil.deleteTestReceivingNote(createdReceivingNote);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testDeleteReceivingNote() throws IOException {
        // add new receivingNote for testing
        Staff staff = TestUtil.addNewStaffForTest();
        ReceivingNote addedReceivingNote = TestUtil.addNewReceivingNoteForTest(staff);

        // test delete receivingNote
        String deleteData = String.format("{\"id\": %d}", addedReceivingNote.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, deleteData, "DELETE");
        ReceivingNote deletedReceivingNote = new Gson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertEquals(addedReceivingNote.getId(), deletedReceivingNote.getId());

        TestUtil.deleteTestStaff(staff);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNote_NonExistId() throws IOException {
        // test delete receivingNote
        String deleteData = String.format("{\"id\": %d}", 99999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, deleteData, "DELETE");
        ReceivingNote deletedReceivingNote = new Gson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(deletedReceivingNote);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNote_NegativeId() throws IOException {
        // test delete receivingNote
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, deleteData, "DELETE");
        ReceivingNote deletedReceivingNote = new Gson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(deletedReceivingNote);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNote_NonNumericId() throws IOException {
        // test delete receivingNote
        String deleteData = String.format("{\"id\": %s}", "nonnumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, deleteData, "DELETE");
        ReceivingNote deletedReceivingNote = new Gson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(deletedReceivingNote);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNote_SpecialCharacterId() throws IOException {
        // test delete receivingNote
        String deleteData = String.format("{\"id\": %s}", "*()*&*");
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, deleteData, "DELETE");
        ReceivingNote deletedReceivingNote = new Gson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertNull(deletedReceivingNote);
    }

    @Test
    public void testUpdateReceivingNote() throws IOException {
        // add new receivingNote for testing
        Staff staff = TestUtil.addNewStaffForTest();
        ReceivingNote addedReceivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        Staff newStaff = TestUtil.addNewStaffForTest();

        // test update receivingNote
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"staff\": {\n" +
                "        \"id\": %d\n" +
                "    }\n" +
                "}", addedReceivingNote.getId(), newStaff.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE, updateData, "PUT");
        ReceivingNote updatedReceivingNote = new Gson().fromJson(json, new TypeToken<ReceivingNote>(){}.getType());
        Assert.assertEquals(newStaff.getId(), updatedReceivingNote.getStaff().getId());

        // delete unit test receivingNote after added
        TestUtil.deleteTestReceivingNote(addedReceivingNote);
        TestUtil.deleteTestStaff(newStaff);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testGetReceivingNoteDetail() throws IOException {
        // add new receivingNoteDetail for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Product product = TestUtil.addNewProductForTest();
        ReceivingNote receivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        ReceivingNoteDetail addedReceivingNoteDetail = TestUtil.addNewReceivingNoteDetailForTest(receivingNote, product);

        // test get receivingNoteDetail
        String url = String.format(TestConfig.URL_RECEIVING_NOTE_DETAIL + "/%d", addedReceivingNoteDetail.getId());
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNoteDetail gotReceivingNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertEquals(addedReceivingNoteDetail.getId(), gotReceivingNoteDetail.getId());

        // delete unit test receivingNoteDetail after added
        TestUtil.deleteTestReceivingNoteDetail(addedReceivingNoteDetail);
        TestUtil.deleteTestReceivingNote(receivingNote);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testGetReceivingNoteDetail_NonExistId() throws IOException {
        // test get receivingNoteDetail
        String url = String.format(TestConfig.URL_RECEIVING_NOTE_DETAIL + "/%d", 99999);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNoteDetail gotReceivingNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(gotReceivingNoteDetail);
    }

    @Test
    public void testGetReceivingNoteDetail_NegativeId() throws IOException {
        // test get receivingNoteDetail
        String url = String.format(TestConfig.URL_RECEIVING_NOTE_DETAIL + "/%d", -1);
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNoteDetail gotReceivingNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(gotReceivingNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testGetReceivingNoteDetail_NonNumericId() throws IOException {
        // test get receivingNoteDetail
        String url = String.format(TestConfig.URL_RECEIVING_NOTE_DETAIL + "/%s", "nonnumeric");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNoteDetail gotReceivingNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(gotReceivingNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testGetReceivingNoteDetail_SpecialCharacterId() throws IOException {
        // test get receivingNoteDetail
        String url = String.format(TestConfig.URL_RECEIVING_NOTE_DETAIL + "/%s", "*((^&");
        String json = TestUtil.getHttpResponse(url, "", "GET");
        ReceivingNoteDetail gotReceivingNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(gotReceivingNoteDetail);
    }

    @Test
    public void testGetAllReceivingNoteDetail() throws IOException {
        // add new receivingNoteDetail for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Product product = TestUtil.addNewProductForTest();
        ReceivingNote receivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        ReceivingNoteDetail addedReceivingNoteDetail = TestUtil.addNewReceivingNoteDetailForTest(receivingNote, product);

        // test get all receivingNoteDetails
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, "", "GET");
        List<ReceivingNoteDetail> receivingNoteDetails = TestUtil.modifiedGson().fromJson(json, new TypeToken<List<ReceivingNoteDetail>>(){}.getType());
        Assert.assertTrue(receivingNoteDetails.size() >= 1);

        // delete unit test receivingNoteDetail after added
        TestUtil.deleteTestReceivingNoteDetail(addedReceivingNoteDetail);
        TestUtil.deleteTestReceivingNote(receivingNote);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

    @Test
    public void testCreateReceivingNoteDetail() throws IOException {
        // test add new receivingNoteDetail
        Staff staff = TestUtil.addNewStaffForTest();
        ReceivingNote receivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        String testData = String.format("{\n" +
                "\t\"receivingNote\": {\"id\": %d},\n" +
                "\t\"quantity\": 10\n" +
                "}", receivingNote.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, testData, "POST");
        ReceivingNoteDetail createdReceivingNoteDetail = TestUtil.modifiedGson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertEquals(10, createdReceivingNoteDetail.getQuantity());

        // delete unit test receivingNoteDetail after added
        TestUtil.deleteTestReceivingNoteDetail(createdReceivingNoteDetail);
        TestUtil.deleteTestReceivingNote(receivingNote);
        TestUtil.deleteTestStaff(staff);
    }

    @Test
    public void testDeleteReceivingNoteDetail() throws IOException {
        // add new receivingNoteDetail for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Product product = TestUtil.addNewProductForTest();
        ReceivingNote receivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        ReceivingNoteDetail addedReceivingNoteDetail = TestUtil.addNewReceivingNoteDetailForTest(receivingNote, product);

        // test delete receivingNoteDetail
        String deleteData = String.format("{\"id\": %d}", addedReceivingNoteDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, deleteData, "DELETE");
        ReceivingNoteDetail deletedReceivingNoteDetail = new Gson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertEquals(addedReceivingNoteDetail.getId(), deletedReceivingNoteDetail.getId());

        TestUtil.deleteTestReceivingNote(receivingNote);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNoteDetail_NonExistId() throws IOException {
        // test delete receivingNoteDetail
        String deleteData = String.format("{\"id\": %d}", 99999);
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, deleteData, "DELETE");
        ReceivingNoteDetail deletedReceivingNoteDetail = new Gson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(deletedReceivingNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNoteDetail_NegativeId() throws IOException {
        // test delete receivingNoteDetail
        String deleteData = String.format("{\"id\": %d}", -1);
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, deleteData, "DELETE");
        ReceivingNoteDetail deletedReceivingNoteDetail = new Gson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(deletedReceivingNoteDetail);
    }

    @Test(expected = IOException.class)
    public void testDeleteReceivingNoteDetail_NonNumericId() throws IOException {
        // test delete receivingNoteDetail
        String deleteData = String.format("{\"id\": %s}", "nonnumeric");
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, deleteData, "DELETE");
        ReceivingNoteDetail deletedReceivingNoteDetail = new Gson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(deletedReceivingNoteDetail);
    }


    @Test(expected = IOException.class)
    public void testDeleteReceivingNoteDetail_SpecialCharacterId() throws IOException {
        // test delete receivingNoteDetail
        String deleteData = String.format("{\"id\": %s}", "*(&*(");
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, deleteData, "DELETE");
        ReceivingNoteDetail deletedReceivingNoteDetail = new Gson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertNull(deletedReceivingNoteDetail);
    }

    @Test
    public void testUpdateReceivingNoteDetail() throws IOException {
        // add new receivingNoteDetail for testing
        Staff staff = TestUtil.addNewStaffForTest();
        Product product = TestUtil.addNewProductForTest();
        ReceivingNote receivingNote = TestUtil.addNewReceivingNoteForTest(staff);
        ReceivingNoteDetail addedReceivingNoteDetail = TestUtil.addNewReceivingNoteDetailForTest(receivingNote, product);

        // test update receivingNoteDetail
        String updateData = String.format("{\n" +
                "    \"id\": %d,\n" +
                "    \"quantity\": 20\n" +
                "}", addedReceivingNoteDetail.getId());
        String json = TestUtil.getHttpResponse(TestConfig.URL_RECEIVING_NOTE_DETAIL, updateData, "PUT");
        ReceivingNoteDetail updatedReceivingNoteDetail = new Gson().fromJson(json, new TypeToken<ReceivingNoteDetail>(){}.getType());
        Assert.assertEquals(20, updatedReceivingNoteDetail.getQuantity());

        // delete unit test receivingNoteDetail after added
        TestUtil.deleteTestReceivingNoteDetail(addedReceivingNoteDetail);
        TestUtil.deleteTestReceivingNote(receivingNote);
        TestUtil.deleteTestStaff(staff);
        TestUtil.deleteTestProduct(product);
    }

}

