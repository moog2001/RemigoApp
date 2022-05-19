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
        Statement statement = c.createStatement();
        statement.executeUpdate("PRAGMA foreign_keys = ON"); // this line allows cascade delete on the sql database
//        resetDatabaseData("agreed");
        resetAsGuest();
    }


    /**
     * test method for the DatabaseHandler class
     * @throws SQLException
     * @author Moog
     * @version 1.0.0
     * @since 2022-5-18
     */
    public void resetAsGuest() throws SQLException {
        PreparedStatement statement = c.prepareStatement("delete from user where first_name='AS' and last_name='GUEST'");
        statement.executeUpdate();
        statement.close();

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

        Memo memoTest = new Memo("test title", "test text", Constants.NULL_INT, LocalDate.now());
        int memoTestId = createMemo(memoTest, userId);
        memoTest.setMemoId(memoTestId);

        Education educationTest = new Education("test title", "test text", Constants.NULL_INT,LocalDate.now(), LocalDate.now(), LocalDate.now(),Constants.NULL_INT,Constants.NULL_INT);
        int educationTestId = createMemoDate(educationTest, userId);
        educationTest.setMemoId(educationTestId);
        User asGuestClone = getUserData(asGuestUser.getUserId()); // add breakpoint and debug to see the result.

    }

    /**
     * use carefully
     * @param password supply correct password
     * @return
     * @throws SQLException
     */
    public boolean resetDatabaseData(String password) throws SQLException {
        if(!password.equals("agreed")){
            return false;
        }

        Statement statement = c.createStatement();
        statement.executeUpdate("delete from user;");
        statement.executeUpdate("delete from memo;");
        statement.executeUpdate("delete from memo_date;");
        statement.executeUpdate("delete from user_memo;");
        statement.executeUpdate("delete from user_memo_date;");
        statement.close();
        return true;
    }


    /**
     * creates User object in the database and returns the id on it.
     * remember to set the returned id/
     * @param user
     * @return id
     * @throws SQLException
     * @author Moog
     * @version 1.0.0
     * @since 2022-5-18
     */
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

        statement.close();
        return userId;
    }

    /**
     *
     * creates Memo object in the database and returns the id in it.
     * remember to set the returned id.
     * @param memoInput
     * @param userIdInput
     * @return
     * @throws SQLException
     * @author Moog
     * @version 1.0.0
     * @since 2022-5-18
     */
    public int createMemo(Memo memoInput, int userIdInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("INSERT INTO memo(type,title,text,create_date,user_id) Values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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

        statement.close();
        return memoId;
    }


    public int createMemoDate(MemoDate memoDateInput, int userIdInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("INSERT INTO memo_date(type,title,text,create_date,last_remind_date,next_remind_date,interval,streak,user_id) Values(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        int type = memoDateInput.getType();
        statement.setInt(1, type);
        statement.setString(2, memoDateInput.getTitle());
        statement.setString(3, memoDateInput.getText());
        statement.setString(4, memoDateInput.getCreateDate().toString());
        statement.setString(5, memoDateInput.getLastRemindDate().toString());
        statement.setString(6, memoDateInput.getNextRemindDate().toString());
        if(type != 2) {
            if (type == 4) {
                statement.setInt(8, ((Education) memoDateInput).getStreak());
            }
            statement.setInt(7, ((MemoDaily)memoDateInput).getInterval());
        }
        statement.setInt(9, userIdInput);
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Count not insert into memo_date");
        }
        ResultSet resultSet = statement.getGeneratedKeys();
        if (!resultSet.next()) {
            throw new SQLException("could not get generated keys from memo_date");
        }
        int memoId = resultSet.getInt(1);
//        memoInput.setMemoId(memoId);
        statement.close();
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

    public User getUserData(int userIdInput) throws SQLException {
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where user_id=" + userIdInput);
        if (!resultSet.next()) {
            return null;
        }
        int userId = userIdInput;
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        int age = resultSet.getInt(4);
        String gender = resultSet.getString(5);
        String userName = resultSet.getString(6);
        String eMail = resultSet.getString(7);
        String password = resultSet.getString(8);
        boolean isGuest = resultSet.getBoolean(9);
        statement.close();
        List<Memo> memoList = getMemoList(userId);
        List<MemoDate> memoDateList = getMemoDateList(userId);
//        List<MemoDate> memoDateList = null;
        return new User(userName, eMail, password, userId, isGuest, firstName, lastName, age, gender, memoList, memoDateList);


    }

    public List<MemoDate> getMemoDateList(int userIdInput) throws SQLException {

        List<MemoDate> memoDateList = new ArrayList<>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user_memo_date where user_id=" + userIdInput);
        if (!resultSet.next()) {
            return null;
        }
        int memoDateId = resultSet.getInt(3);
        resultSet = statement.executeQuery("select * from memo_date where memo_date_id=" + memoDateId);
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
                    break;
                }
                case 4: {
                    int streak = resultSet.getInt(9);
                    memoDate = new Education(title, text, memoDateId, createDate, lastRemindDate, nextRemindDate, interval, streak);
                    memoDateList.add(memoDate);
                    break;
                }
            }

        }
        statement.close();
        return memoDateList;
    }


    public List<Memo> getMemoList(int userIdInput) throws SQLException {

        List<Memo> memoList = new ArrayList<>();
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user_memo where user_id=" + userIdInput);
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