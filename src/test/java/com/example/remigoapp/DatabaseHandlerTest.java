package com.example.remigoapp;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {

    DatabaseHandler databaseHandler = new DatabaseHandler();
    String firstName = "AS";
    String lastName = "GUEST";
    int age = 0;
    String gender = "FEMALE";
    String userName = "AS GUEST username";
    String eMail = "AS GUEST email";
    String password = "AS GUEST password";
    boolean isGuest = false;
    int id;
    User user;

    DatabaseHandlerTest() throws SQLException, ClassNotFoundException {
    }


    private void clear() throws SQLException {
        databaseHandler.resetAsGuest();
        User userTest = databaseHandler.getUserData(userName);
        this.id = userTest.getUserId();
        this.user = userTest;
    }

    @Test
    void createUserTest() throws SQLException {
        clear();
        User asGuestUser = new User(userName, eMail, password, Constants.NULL_INT, isGuest, firstName, lastName, age, gender);
        int userId = databaseHandler.createUser(asGuestUser);
        asGuestUser.setUserId(userId);
        assertTrue(asGuestUser.getUserId() != Constants.NULL_INT);
    }

    @Test
    void createMemoTest() throws SQLException {
        clear();
        Memo memoTest = new Memo("test title", "test text", Constants.NULL_INT, LocalDate.now());
        int memoTestId = databaseHandler.createMemo(memoTest, 1);
        memoTest.setMemoId(memoTestId);
        assertTrue(memoTest.getMemoId() != Constants.NULL_INT);
    }


    @Test
    void createMemoDateTest() throws SQLException {
        clear();
        Education educationTest = new Education("test title", "test text", Constants.NULL_INT, LocalDate.now(), LocalDate.now(), LocalDate.now(), Constants.NULL_INT, Constants.NULL_INT);
        int educationTestId = databaseHandler.createMemoDate(educationTest, 1);
        educationTest.setMemoId(educationTestId);
        assertTrue(educationTest.getMemoId() != Constants.NULL_INT);
    }

    @Test
    void updateUserTest() throws SQLException {
        clear();
        this.user.setEMail("updated for test");
        databaseHandler.updateUserData(this.user);
        User userTest = databaseHandler.getUserData(userName);
        assertEquals("updated for test", userTest.getEMail());
    }

    @Test
    void updateMemoTest() throws SQLException {
        clear();
        Memo memoTest = new Memo("test title", "test text", Constants.NULL_INT, LocalDate.now());
        int memoTestId = databaseHandler.createMemo(memoTest, this.id);
        memoTest.setMemoId(memoTestId);

        memoTest.setTitle("test update title");
        boolean testUpdate = databaseHandler.updateMemo(memoTest);
        Memo memoTestClone = memoTest;
        assertEquals("test update title", memoTest.getTitle());
    }
    @Test
    void updateMemoDateTest() throws SQLException {
        clear();
        Education educationTest = new Education("test title", "test text", Constants.NULL_INT, LocalDate.now(), LocalDate.now(), LocalDate.now(), Constants.NULL_INT, Constants.NULL_INT);
        int educationTestId = databaseHandler.createMemoDate(educationTest, this.id);
        educationTest.setMemoId(educationTestId);

        educationTest.setTitle("test update title");
        boolean testUpdateEducation = databaseHandler.updateMemoDate(educationTest);
        Education educationTestClone = educationTest;
        assertEquals("test update title", educationTest.getTitle());
    }


}