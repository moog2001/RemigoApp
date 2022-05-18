package com.example.remigoapp;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler {
    Connection c = null;


    DatabaseHandler() throws SQLException, ClassNotFoundException {
        connect();
    }

    private void connect() throws SQLException, ClassNotFoundException {
        Class.forName(Constants.JCDB_CLASS);
        c = DriverManager.getConnection(Constants.JCDB_CONNECTION);
    }

    public void resetAsGuest() throws SQLException {
        Statement statement = c.createStatement();
        statement.executeQuery("delete from user where first_name = \"AS\" and last_name=\"GUEST\"");

        String firstName = "AS";
        String lastName = "GUEST";
        int age = 0;
        String gender = "FEMALE";
        String userName = "AS GUEST username";
        String eMail = "AS GUEST email";
        String password = "AS GUEST password";
        boolean isGuest = false;
        User asGuestUser = new User(userName,eMail,password,Constants.NULL_INT,isGuest,firstName,lastName,age,gender);
        int userId = createUser(asGuestUser);
        asGuestUser.setUserId(userId);
    }

    public int createUser(User user) throws SQLException {


        PreparedStatement statement = c.prepareStatement("INSERT INTO user (first_name, last_name, age, gender, user_name, e_mail, password, is_guest) Values('" + user.getFirstName() + "','" + user.getLastName() + "'," + String.valueOf(user.getAge()) + ",'" + user.getGender() + "','" + user.getUserName() + "','" + user.getEMail() + "','" + user.getPassword() + "'," + String.valueOf(user.isGuest()) + ");" , Statement.RETURN_GENERATED_KEYS);
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Could not insert into AS GUEST");
        }
        ResultSet resultSet = statement.getGeneratedKeys();
        if (!resultSet.next()) {
            throw new SQLException("Could not get generated keys from AS GUEST");
        }
        int userId = resultSet.getInt(1);
        return userId;
    }

    private int createMemo(Memo memoInput, int userIdInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("INSERT INTO memo Values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, memoInput.getType());
        statement.setString(2, memoInput.getTitle());
        statement.setString(3, memoInput.getText());
        statement.setString(4, memoInput.getCreateDate().toString());
        statement.setInt(5, userIdInput);
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Count not insert into user_memo");
        }
        ResultSet resultSet = statement.getGeneratedKeys();
        if (!resultSet.next()) {
            throw new SQLException("could not get generated keys from user_memo");
        }

        int memoId = resultSet.getInt(1);
//        memoInput.setMemoId(memoId);
        return memoId;
    }
//
//    private int createUserMemo(int userIdInput, int memoIdInput) throws SQLException {
//        PreparedStatement statement = c.prepareStatement("INSERT INTO user_memo Values(?, ?)", Statement.RETURN_GENERATED_KEYS);
//        statement.setInt(1, userIdInput);
//        statement.setInt(2, memoIdInput);
//
//        int affectedRows = statement.executeUpdate();
//        if(affectedRows == 0){
//            throw new SQLException("Count not insert into user_memo");
//        }
//
//        ResultSet resultSet = statement.getGeneratedKeys();
//        if(!resultSet.next()){
//            throw new SQLException("could not get generated keys from user_memo");
//        }
//        return resultSet.getInt(1);
//
//    };

    public User getUserData(String userIdInput) throws SQLException {
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where user_id=" + userIdInput);
        if (!resultSet.next()) {
            return null;
        }
        int userId = resultSet.getInt(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        int age = resultSet.getInt(4);
        String gender = resultSet.getString(5);
        String userName = resultSet.getString(6);
        String eMail = resultSet.getString(7);
        int userMemoId = resultSet.getInt(8);
        int userMemoDateId = resultSet.getInt(9);
        String password = resultSet.getString(10);
        boolean isGuest = resultSet.getBoolean(11);
        statement.close();
        List<Memo> memoList = getMemoList(userMemoId);
        List<MemoDate> memoDateList = getMemoDateList(userMemoDateId);
        return new User(userName, eMail, password, userId, isGuest, firstName, lastName, age, gender, memoList, memoDateList);


    }

    public List<MemoDate> getMemoDateList(int userMemoDateIdInput) throws SQLException {

        List<MemoDate> memoDateList = new ArrayList<>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user_memo_date where user_memo_id=" + userMemoDateIdInput);
        if (!resultSet.next()) {
            return null;
        }
        int memoDateId = resultSet.getInt(3);
        resultSet = statement.executeQuery("select * from memo where memo_date_id=" + memoDateId);
        while (resultSet.next()) {
            int type = resultSet.getInt(2);
            String title = resultSet.getString(3);
            String text = resultSet.getString(4);
            LocalDate createDate = LocalDate.parse(resultSet.getString(5));
            LocalDate lastRemindDate = LocalDate.parse(resultSet.getString(6));
            LocalDate nextRemindDate = LocalDate.parse(resultSet.getString(7));
            int interval = Constants.NULL_INT; // default null value
            MemoDate memoDate;
            switch (type) {
                case 2: {
                    memoDate = new MemoDate(title, text, memoDateId, createDate, lastRemindDate, nextRemindDate);
                    memoDateList.add(memoDate);
                    break;
                }
                case 3: {
                    interval = resultSet.getInt(8);
                    memoDate = new MemoDaily(title, text, memoDateId, createDate, lastRemindDate, nextRemindDate, interval);
                    memoDateList.add(memoDate);
                }
                case 4: {
                    int streak = resultSet.getInt(9);
                    memoDate = new Education(title, text, memoDateId, createDate, lastRemindDate, nextRemindDate, interval, streak);
                    memoDateList.add(memoDate);
                }
            }

        }
        statement.close();
        return memoDateList;
    }


    public List<Memo> getMemoList(int userMemoIdInput) throws SQLException {

        List<Memo> memoList = new ArrayList<>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user_memo where user_memo_id=" + userMemoIdInput);
        if (!resultSet.next()) {
            return null;
        }
        int memoId = resultSet.getInt(3);
        resultSet = statement.executeQuery("select * from memo where memo_id=" + memoId);
        while (resultSet.next()) {
            int type = resultSet.getInt(2);
            String title = resultSet.getString(3);
            String text = resultSet.getString(4);
            LocalDate createDate = LocalDate.parse(resultSet.getString(5));
            Memo memo = new Memo(title, text, memoId, createDate);
            memoList.add(memo);
        }
        statement.close();
        return memoList;
    }


}