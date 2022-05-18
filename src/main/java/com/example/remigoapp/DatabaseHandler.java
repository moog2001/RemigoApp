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
    }

    public boolean createUser() throws SQLException {
        PreparedStatement statement = c.prepareStatement("INSERT INTO user (first_name, last_name,age,gender,user_name,e_mail) Values('AS', 'GUEST',0,'FEMALE','ASGUEST','ASGUEST')", Statement.RETURN_GENERATED_KEYS);
        int affectedRows = statement.executeUpdate();

        if(affectedRows == 0){
            throw new SQLException("Could not insert into AS GUEST");
            return false;
        }
        ResultSet resultSet = statement.getGeneratedKeys();
        if(!resultSet.next()){
            throw new SQLException("Could not get generated keys from AS GUEST");
            return false;
        }
        resultSet.getInt(1);





    }

    private bool createUserMemo

    public User getUserData(String userIdInput) throws SQLException {
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where user_id="+userIdInput);
        if(!resultSet.next()){
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
        return new User(userName,eMail,password,userId,isGuest,firstName,lastName,age,gender,memoList,memoDateList);



    }

    public List<MemoDate> getMemoDateList(int userMemoDateIdInput) throws SQLException {

        List<MemoDate> memoDateList = new ArrayList<>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user_memo_date where user_memo_id="+userMemoDateIdInput);
        if(!resultSet.next()) {
            return null;
        }
        int memoDateId = resultSet.getInt(3);
        resultSet = statement.executeQuery("select * from memo where memo_date_id="+memoDateId);
        while(resultSet.next()){
            int type = resultSet.getInt(2);
            String title = resultSet.getString(3);
            String text = resultSet.getString(4);
            LocalDate createDate = LocalDate.parse(resultSet.getString(5));
            LocalDate lastRemindDate = LocalDate.parse(resultSet.getString(6));
            LocalDate nextRemindDate = LocalDate.parse(resultSet.getString(7));
            int interval = Constants.NULL_INT; // default null value
            MemoDate memoDate;
            switch (type){
                case 2:{
                    memoDate = new MemoDate(title,text,memoDateId,createDate,lastRemindDate,nextRemindDate);
                    memoDateList.add(memoDate);
                    break;
                }
                case 3:{
                    interval = resultSet.getInt(8);
                    memoDate = new MemoDaily(title,text,memoDateId,createDate,lastRemindDate,nextRemindDate, interval);
                    memoDateList.add(memoDate);
                }
                case 4:{
                    int streak = resultSet.getInt(9);
                    memoDate = new Education(title,text,memoDateId,createDate,lastRemindDate,nextRemindDate,interval,streak);
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
        ResultSet resultSet = statement.executeQuery("select * from user_memo where user_memo_id="+userMemoIdInput);
        if(!resultSet.next()) {
            return null;
        }
        int memoId = resultSet.getInt(3);
        resultSet = statement.executeQuery("select * from memo where memo_id="+memoId);
        while(resultSet.next()){
            int type = resultSet.getInt(2);
            String title = resultSet.getString(3);
            String text = resultSet.getString(4);
            LocalDate createDate = LocalDate.parse(resultSet.getString(5));
            Memo memo = new Memo(title,text,memoId,createDate);
            memoList.add(memo);
        }
        statement.close();
        return memoList;
    }





}