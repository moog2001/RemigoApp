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
//        resetAsGuest();
    }


    /**
     * test method for the DatabaseHandler class
     *
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


        User asGuestUser = new User(userName, eMail, password, Constants.NULL_INT, isGuest, firstName, lastName, age, gender);
        int userId = createUser(asGuestUser);
        asGuestUser.setUserId(userId);

        Memo memoTest = new Memo("test title", "test text", Constants.NULL_INT, LocalDate.now());
        int memoTestId = createMemo(memoTest, userId);
        memoTest.setMemoId(memoTestId);

        MemoDate memoDateTest = new MemoDate("test title", "test text", Constants.NULL_INT, LocalDate.now(), LocalDate.now(), LocalDate.now());
        int memoDateTestId = createMemoDate(memoDateTest, userId);
        memoDateTest.setMemoId(memoDateTestId);

        MemoDaily memoDailyTest = new MemoDaily("test title", "test text", Constants.NULL_INT, LocalDate.now(), LocalDate.now(), LocalDate.now(), Constants.NULL_INT);
        int memoDailyTestId = createMemoDate(memoDailyTest, userId);
        memoDailyTest.setMemoId(memoDailyTestId);

        Education educationTest = new Education("test title", "test text", Constants.NULL_INT, LocalDate.now(), LocalDate.now(), LocalDate.now(), Constants.NULL_INT, Constants.NULL_INT);
        int educationTestId = createMemoDate(educationTest, userId);
        educationTest.setMemoId(educationTestId);

        User asGuestClone = getUserData(asGuestUser.getUserId()); // add breakpoint and debug to see the result.

        asGuestClone.setUserName("updated AS GUEST username");
        updateUserData(asGuestClone);
        asGuestClone = getUserData(asGuestClone.getUserId());

        memoTest.setTitle("test update title");
        boolean testUpdate = updateMemo(memoTest);
        Memo memoTestClone = memoTest;

        memoDateTest.setTitle("test update title");
        boolean testUpdateDate = updateMemoDate(memoDateTest);
        MemoDate memoDateTestClone = memoDateTest;

        memoDailyTest.setTitle("test update title");
        boolean testUpdateDaily = updateMemoDate(educationTest);
        MemoDaily memoDailyTestClone = memoDailyTest;

        educationTest.setTitle("test update title");
        boolean testUpdateEducation = updateMemoDate(educationTest);
        Education educationTestClone = educationTest;


//        deleteMemo(memoTest);
//        deleteMemoDate(educationTest);

        asGuestClone = getUserData(asGuestClone.getUserId());


    }

    /**
     * notify update to the database and save
     *
     * @param memoDateInput
     * @return
     * @throws SQLException
     */
    public boolean updateMemoDate(MemoDate memoDateInput) throws SQLException {

        int type = memoDateInput.getType();
        String query;
        switch (type) {
            case Constants.TYPE_MEMO_DATE: {
                query = "UPDATE memo_date " +
                        "SET " +
                        "title = '" + memoDateInput.getTitle() + "', " +
                        "text = '" + memoDateInput.getText() + "', " +
                        "create_date = '" + memoDateInput.getCreateDate().toString() + "', " +
                        "last_remind_date = '" + memoDateInput.getLastRemindDate().toString() + "', " +
                        "next_remind_date = '" + memoDateInput.getNextRemindDate().toString() + "' " +
                        "WHERE memo_date_id =" + memoDateInput.getMemoId();
                break;
            }
            case Constants.TYPE_MEMO_DAILY: {
                query = "UPDATE memo_date " +
                        "SET " +
                        "title = '" + memoDateInput.getTitle() + "', " +
                        "text = '" + memoDateInput.getText() + "', " +
                        "create_date = '" + memoDateInput.getCreateDate().toString() + "', " +
                        "last_remind_date = '" + memoDateInput.getLastRemindDate().toString() + "', " +
                        "next_remind_date = '" + memoDateInput.getNextRemindDate().toString() + "', " +
                        "interval = " + ((MemoDaily) memoDateInput).getInterval() + " " +
                        "WHERE memo_date_id =" + memoDateInput.getMemoId();
                break;
            }
            case Constants.TYPE_EDUCATION: {
                query = "UPDATE memo_date " +
                        "SET " +
                        "title = '" + memoDateInput.getTitle() + "', " +
                        "text = '" + memoDateInput.getText() + "', " +
                        "create_date = '" + memoDateInput.getCreateDate().toString() + "', " +
                        "last_remind_date = '" + memoDateInput.getLastRemindDate().toString() + "', " +
                        "next_remind_date = '" + memoDateInput.getNextRemindDate().toString() + "', " +
                        "interval = " + ((MemoDaily) memoDateInput).getInterval() + ", " +
                        "streak = " + ((Education) memoDateInput).getStreak() + " " +
                        "WHERE memo_date_id =" + memoDateInput.getMemoId();
                break;
            }
            default: {
                throw new SQLException("Invalid type in memo date update");
            }
        }


        Statement statement = c.createStatement();
        int affectedRows = statement.executeUpdate(query);

        if (affectedRows == 0) {
            throw new SQLException("Could not update memo date:" + memoDateInput.getMemoId());
        }
        statement.close();
        return true;
    }

    /**
     * notify delete to the database and save it there
     *
     * @param memoDateInput
     * @return
     * @throws SQLException
     */
    public boolean deleteMemoDate(MemoDate memoDateInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("DELETE from memo_date WHERE memo_date_id=" + memoDateInput.getMemoId() + ";");
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Could not insert into AS GUEST");
        }

        statement.close();
        return true;
    }

    /**
     * notify delete to the database and save it there
     *
     * @param memoDateIdInput
     * @return
     * @throws SQLException
     */
    public boolean deleteMemoDate(int memoDateIdInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("DELETE from user_memo_date WHERE memo_date_id=" + memoDateIdInput + ";");
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Could not insert into AS GUEST");
        }

        statement.close();
        return true;
    }

    /**
     * notify update to the database and save it there
     *
     * @param memoInput
     * @return
     * @throws SQLException
     */
    public boolean updateMemo(Memo memoInput) throws SQLException {
        String query = "UPDATE memo " +
                "SET " +
                "title = '" + memoInput.getTitle() + "', " +
                "text = '" + memoInput.getText() + "', " +
                "create_date = '" + memoInput.getCreateDate().toString() + "' " +
                "WHERE memo_id =" + memoInput.getMemoId();

        Statement statement = c.createStatement();
        int affectedRows = statement.executeUpdate(query);

        if (affectedRows == 0) {
            throw new SQLException("Could not update memo:" + memoInput.getMemoId());
        }
        statement.close();
        return true;
    }


    /**
     * notify delete to the database and save it there
     *
     * @param memoInput
     * @return
     * @throws SQLException
     */
    public boolean deleteMemo(Memo memoInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("DELETE from memo WHERE memo_id=" + memoInput.getMemoId() + ";");
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Could not insert into AS GUEST");
        }

        statement.close();


        return true;
    }

    /**
     * notify delete to the database and save it there
     *
     * @param memoInputId
     * @return
     * @throws SQLException
     */
    public boolean deleteMemo(int memoInputId) throws SQLException {
        PreparedStatement statement = c.prepareStatement("DELETE from memo WHERE memo_id=" + memoInputId + ";");
        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Could not insert into AS GUEST");
        }

        statement.close();


        return true;
    }

    /**
     * notify update to the database and save it there
     *
     * @param userInput
     * @return
     * @throws SQLException
     */
    public boolean updateUserData(User userInput) throws SQLException {

        int isGuestAsInt;
        if (userInput.isGuest()) {
            isGuestAsInt = 1;
        } else {
            isGuestAsInt = 0;
        }

        String query = "UPDATE user " +
                "SET " +
                "first_name = '" + userInput.getFirstName() + "', " +
                "last_name = '" + userInput.getLastName() + "', " +
                "age = " + userInput.getAge() + ", " +
                "gender = '" + userInput.getGender() + "', " +
                "user_name = '" + userInput.getUserName() + "', " +
                "e_mail = '" + userInput.getEMail() + "', " +
                "password = '" + userInput.getPassword() + "', " +
                "is_guest = " + isGuestAsInt + " " +
                "WHERE user_id =" + userInput.getUserId();

        Statement statement = c.createStatement();
        int affectedRows = statement.executeUpdate(query);

        if (affectedRows == 0) {
            throw new SQLException("Could not update user:" + userInput.getUserId());
        }
        statement.close();
        return true;
    }

    /**
     * use carefully
     *
     * @param password supply correct password
     * @return
     * @throws SQLException
     */
    public boolean resetDatabaseData(String password) throws SQLException {
        if (!password.equals("agreed")) {
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
     *
     * @param user
     * @return id
     * @throws SQLException
     * @author Moog
     * @version 1.0.0
     * @since 2022-5-18
     */
    public int createUser(User user) throws SQLException {


        PreparedStatement statement = c.prepareStatement("INSERT INTO user (first_name, last_name, age, gender, user_name, e_mail, password, is_guest) Values('" + user.getFirstName() + "','" + user.getLastName() + "'," + String.valueOf(user.getAge()) + ",'" + user.getGender() + "','" + user.getUserName() + "','" + user.getEMail() + "','" + user.getPassword() + "'," + String.valueOf(user.isGuest()) + ");", Statement.RETURN_GENERATED_KEYS);
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
     * creates Memo object in the database and returns the id in it.
     * remember to set the returned id.
     *
     * @param memoInput
     * @param userIdInput
     * @return
     * @throws SQLException
     * @author Moog
     * @version 1.0.0
     * @since 2022-5-18
     */
    public int createMemo(Memo memoInput, int userIdInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("INSERT INTO memo(type,title,text,create_date, user_id) Values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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

    /**
     * creates MemoDate in the database
     *
     * @param memoDateInput
     * @param userIdInput
     * @return id of the MemoDate object
     * @throws SQLException
     */
    public int createMemoDate(MemoDate memoDateInput, int userIdInput) throws SQLException {
        PreparedStatement statement = c.prepareStatement("INSERT INTO memo_date(type,title,text,create_date,last_remind_date,next_remind_date,interval,streak,user_id) Values(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        int type = memoDateInput.getType();
        statement.setInt(1, type);
        statement.setString(2, memoDateInput.getTitle());
        statement.setString(3, memoDateInput.getText());
        statement.setString(4, memoDateInput.getCreateDate().toString());
        statement.setString(5, memoDateInput.getLastRemindDate().toString());
        statement.setString(6, memoDateInput.getNextRemindDate().toString());
        if (type != Constants.TYPE_MEMO_DATE) {
            if (type == Constants.TYPE_EDUCATION) {
                statement.setInt(8, ((Education) memoDateInput).getStreak());
            }
            statement.setInt(7, ((MemoDaily) memoDateInput).getInterval());
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

    /**
     * returns the User object
     *
     * @param userIdInput user id
     * @return
     * @throws SQLException
     */
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
        User user = new User(userName, eMail, password, userId, isGuest, firstName, lastName, age, gender, memoList, memoDateList);

        return user;
    }

    public User getUserData(String userName) throws SQLException {
        Statement statement = c.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where user_name='" + userName + "'");
        if (!resultSet.next()) {
//            throw new SQLException("No user found with username: " + userName);
            return null;
        }
        int userId = resultSet.getInt(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        int age = resultSet.getInt(4);
        String gender = resultSet.getString(5);
        String eMail = resultSet.getString(7);
        String password = resultSet.getString(8);
        boolean isGuest = resultSet.getBoolean(9);
        statement.close();
        List<Memo> memoList = getMemoList(userId);
        List<MemoDate> memoDateList = getMemoDateList(userId);
//        List<MemoDate> memoDateList = null;
        User user = new User(userName, eMail, password, userId, isGuest, firstName, lastName, age, gender, memoList, memoDateList);

        return user;
    }

    /**
     * returns the MemoDate List of the User
     *
     * @param userIdInput user id
     * @return
     * @throws SQLException
     */
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

    /**
     * returns the Memo List of the User
     *
     * @param userIdInput user id
     * @return
     * @throws SQLException
     */
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