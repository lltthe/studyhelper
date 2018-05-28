package com.example.lamlethanhthe.studyhelper.DataModules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DataManager extends SQLiteOpenHelper {
    private static final int databaseVersion = 1;
    private static final String databaseName = "VNNTHandbook";

    public DataManager(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    private static final String TABLE_UNIT = "units";
    private static final String UNIT_ID = "id";
    private static final String UNIT_NAME = "name";

    private static final String TABLE_HOST = "hosts";
    private static final String HOST_NAME = "name";
    private static final String HOST_UNIT = "unit";
    private static final String HOST_WEB = "website";
    private static final String HOST_PHONE = "phone";
    private static final String HOST_ADDRESS = "address";

    private static final String TABLE_TEST = "tests";
    private static final String TEST_DATETIME = "datetime";
    private static final String TEST_NAME = "name";
    private static final String TEST_TYPE = "type";
    private static final String TEST_COMP = "compulsory";

    private static final String TABLE_SUBJECT = "subjects";
    private static final String SUBJECT_NAME = "name";
    private static final String SUBJECT_TEST = "test";

    private static final String TABLE_PROFILE = "profiles";
    private static final String PROFILE_NAME = "username";
    private static final String PROFILE_HSCHOOL = "fromHighSchool";
    private static final String PROFILE_HOST = "host";

    private static final String TABLE_LOCATION = "locations";
    private static final String LOC_USER = "user";
    private static final String LOC_NAME = "name";
    private static final String LOC_ADDRESS = "address";
    private static final String LOC_TYPE = "type";
    private static final String LOC_LAT = "lat";
    private static final String LOC_LONG = "long";

    private static final String TABLE_TESTCHOICE = "test_choices";
    private static final String TC_USER = "user";
    private static final String TC_TEST = "test";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_UNIT = "CREATE TABLE " + TABLE_UNIT + "("
                + UNIT_ID + " INTEGER PRIMARY KEY," + UNIT_NAME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_UNIT);

        String CREATE_TABLE_HOST = "CREATE TABLE " + TABLE_HOST + "("
                + HOST_NAME + " TEXT," + HOST_UNIT + " INTEGER," + HOST_WEB + " TEXT," + HOST_PHONE + " TEXT," + HOST_ADDRESS + " TEXT,"
                + "FOREIGN KEY(" + HOST_UNIT + ") REFERENCES " + TABLE_UNIT + "(" + UNIT_ID + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                + "PRIMARY KEY(" + HOST_NAME + "," + HOST_UNIT + ")" + ")";
        db.execSQL(CREATE_TABLE_HOST);

        String CREATE_TABLE_TEST = "CREATE TABLE " + TABLE_TEST + "("
                + TEST_NAME + " TEXT PRIMARY KEY," + TEST_DATETIME + " TEXT," + TEST_TYPE + " TEXT," + TEST_COMP + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE_TEST);

        String CREATE_TABLE_SUBJECT = "CREATE TABLE " + TABLE_SUBJECT + "("
                + SUBJECT_NAME + " TEXT PRIMARY KEY," + SUBJECT_TEST + " TEXT,"
                + "FOREIGN KEY(" + SUBJECT_TEST + ") REFERENCES " + TABLE_TEST + "(" + TEST_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
        db.execSQL(CREATE_TABLE_SUBJECT);

        String CREATE_TABLE_PROFILE = "CREATE TABLE " + TABLE_PROFILE + "("
                + PROFILE_NAME + " TEXT PRIMARY KEY," + PROFILE_HSCHOOL + " INTEGER," + PROFILE_HOST + " TEXT,"
                + "FOREIGN KEY(" + PROFILE_HOST + ") REFERENCES " + TABLE_HOST + "(" + HOST_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE" + ")";
        db.execSQL(CREATE_TABLE_PROFILE);

        String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION + "("
                + LOC_USER + " TEXT," + LOC_LAT + " REAL," + LOC_LONG + " REAL," + LOC_NAME + " TEXT,"
                + LOC_ADDRESS + " TEXT," + LOC_TYPE + " TEXT,"
                + "FOREIGN KEY(" + LOC_USER + ") REFERENCES " + TABLE_PROFILE + "(" + PROFILE_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                + "PRIMARY KEY(" + LOC_USER + "," + LOC_LAT + "," + LOC_LONG + "," + LOC_ADDRESS + ")" + ")";
        db.execSQL(CREATE_TABLE_LOCATION);

        String CREATE_TABLE_TESTCHOICE = "CREATE TABLE " + TABLE_TESTCHOICE + "("
                + TC_USER + " TEXT," + TC_TEST + " TEXT,"
                + "FOREIGN KEY(" + TC_USER + ") REFERENCES " + TABLE_PROFILE + "(" + PROFILE_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                + "FOREIGN KEY(" + TC_TEST + ") REFERENCES " + TABLE_TEST + "(" + TEST_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE,"
                + "PRIMARY KEY(" + TC_USER + "," + TC_TEST + ")" + ")";
        db.execSQL(CREATE_TABLE_TESTCHOICE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIT);

        onCreate(db);
    }

    public void initLocalData() {
        addUnit(new Unit(1, "Hà Nội"));
        addUnit(new Unit(2, "TP. Hồ Chí Minh"));
        addUnit(new Unit(3, "Hải Phòng"));
        addUnit(new Unit(4, "Hà Giang"));
        addUnit(new Unit(5, "Cao Bằng"));
        addUnit(new Unit(6, "Lai Châu"));
        addUnit(new Unit(7, "Điện Biên"));
        addUnit(new Unit(8, "Lào Cai"));
        addUnit(new Unit(9, "Tuyên Quang"));
        addUnit(new Unit(10, "Lạng Sơn"));
        addUnit(new Unit(11, "Bắc Kạn"));
        addUnit(new Unit(12, "Thái Nguyên"));
        addUnit(new Unit(13, "Yên Bái"));
        addUnit(new Unit(14, "Sơn La"));
        addUnit(new Unit(15, "Phú Thọ"));
        addUnit(new Unit(16, "Vĩnh Phúc"));
        addUnit(new Unit(17, "Quảng Ninh"));
        addUnit(new Unit(18, "Bắc Giang"));
        addUnit(new Unit(19, "Bắc Ninh"));
        addUnit(new Unit(20, "Hải Dương"));
        addUnit(new Unit(21, "Hưng Yên"));
        addUnit(new Unit(22, "Hoà Bình"));
        addUnit(new Unit(23, "Hà Nam"));
        addUnit(new Unit(24, "Nam Định"));
        addUnit(new Unit(25, "Thái Bình"));
        addUnit(new Unit(26, "Ninh Bình"));
        addUnit(new Unit(27, "Thanh Hoá"));
        addUnit(new Unit(28, "Nghệ An"));
        addUnit(new Unit(29, "Hà Tĩnh"));
        addUnit(new Unit(30, "Quảng Bình"));
        addUnit(new Unit(31, "Quảng Trị"));
        addUnit(new Unit(32, "Thừa Thiên Huế"));
        addUnit(new Unit(33, "Đà Nẵng"));
        addUnit(new Unit(34, "Quảng Nam"));
        addUnit(new Unit(35, "Quảng Ngãi"));
        addUnit(new Unit(36, "Bình Định"));
        addUnit(new Unit(37, "Phú Yên"));
        addUnit(new Unit(38, "Gia Lai"));
        addUnit(new Unit(39, "Kon Tum"));
        addUnit(new Unit(40, "Đắk Lắk"));
        addUnit(new Unit(41, "Đắk Nông"));
        addUnit(new Unit(42, "Khánh Hoà"));
        addUnit(new Unit(43, "Ninh Thuận"));
        addUnit(new Unit(44, "Bình Thuận"));
        addUnit(new Unit(45, "Lâm Đồng"));
        addUnit(new Unit(46, "Bình Phước"));
        addUnit(new Unit(47, "Bình Dương"));
        addUnit(new Unit(48, "Tây Ninh"));
        addUnit(new Unit(49, "Đồng Nai"));
        addUnit(new Unit(50, "Long An"));
        addUnit(new Unit(51, "Đồng Tháp"));
        addUnit(new Unit(52, "An Giang"));
        addUnit(new Unit(53, "Bà Rịa - Vũng Tàu"));
        addUnit(new Unit(54, "Tiền Giang"));
        addUnit(new Unit(55, "Cần Thơ"));
        addUnit(new Unit(56, "Hậu Giang"));
        addUnit(new Unit(57, "Bến Tre"));
        addUnit(new Unit(58, "Vĩnh Long"));
        addUnit(new Unit(59, "Trà Vinh"));
        addUnit(new Unit(60, "Sóc Trăng"));
        addUnit(new Unit(61, "Bạc Liêu"));
        addUnit(new Unit(62, "Kiên Giang"));
        addUnit(new Unit(63, "Cà Mau"));

        addHost(new Host("default0", 2, "https://www.google.com.vn/?gfe_rd=cr&ei=LnpHWeTwMI-l8weer5zwDg&gws_rd=ssl", "0979093710", ""));
        addHost(new Host("ĐH Quốc gia TP.HCM", 2, "http://www.vnuhcm.edu.vn/", "0837242181", "Khu Phố 06, Linh Trung, Thủ Đức, TP. Hồ Chí Minh"));
        addHost(new Host("ĐH Sài Gòn", 2, "http://sgu.edu.vn/", "0838354409", "273 An Dương Vương, Q5, TP. Hồ Chí Minh"));
        addHost(new Host("ĐH Sư phạm TP.HCM", 2, "http://www.hcmup.edu.vn/", "0838352020", "280 An Dương Vương, P4, Q5, TP. Hồ Chí Minh"));
        addHost(new Host("ĐH Nguyễn Tất Thành", 2, "http://ntt.edu.vn/web/", "0839411211", "300A Nguyễn Tất Thành, Phường 13, Q4, TP. Hồ Chí Minh"));
        addHost(new Host("ĐH Hoa Sen", 2, "http://www.hoasen.edu.vn/vi", "0873007272", "P.NZ001, 08 Nguyễn Văn Tráng, P. Bến Thành, Q1, TP. Hồ Chí Minh"));
        addHost(new Host("ĐH Văn Hiến", 2, "http://www.vhu.edu.vn/", "0838320333", "665-667-669 Điện Biên Phủ, P.1, Q3, TP.HCM"));
        addHost(new Host("ĐH Ngoại ngữ - Tin học TP.HCM", 2, "http://www.huflit.edu.vn/", "0838632052", "155 Sư Vạn Hạnh (ND), Phường 13, Quận 10, TP.HCM"));
        addHost(new Host("ĐH Quốc tế Hồng Bàng", 2, "http://hbu.edu.vn/", "0838116486", "Số 3 - Hoàng Việt - P.4 - Q.Tân Bình - TP.HCM"));
        addHost(new Host("ĐH Y khoa Phạm Ngọc Thạch", 2, "http://www.pnt.edu.vn", "0838652435", "86/2 Thành Thái, Q.10, TPHCM"));

        addHost(new Host("ĐH Quốc gia Hà Nội", 1, "http://www.vnu.edu.vn/home/", "0437547670", "144 đường Xuân Thủy, Quận Cầu Giấy, Hà Nội"));
        addHost(new Host("ĐH Bách khoa Hà Nội", 1, "https://hust.edu.vn/", "0436231732", "Số 1 Đại Cồ Việt, Hai Bà Trưng, Hà Nội"));
        addHost(new Host("ĐH Hà Nội", 1, "http://www.hanu.edu.vn/vn/", "0438544338", "Km 9, đường Nguyễn Trãi, quận Thanh Xuân, Hà Nội"));
        addHost(new Host("ĐH Công nghệ Giao thông Vận tải", 1, "http://utt.edu.vn/", "0438544264", "Số 54 Triều Khúc, Thanh Xuân, Hà Nội"));
        addHost(new Host("ĐH Lâm Nghiệp", 1, "http://vnuf.edu.vn/", "02433840233", "Thị trấn Xuân Mai, huyện Chương Mỹ, Hà Nội"));
        addHost(new Host("ĐH Y Hà Nội", 1, "http://www.hmu.edu.vn/", "0438523798", "01 Tôn Thất Tùng-Đống Đa-Hà Nội"));
        addHost(new Host("ĐH Công nghiệp Hà Nội", 1, "https://www.haui.edu.vn/vn", "0437655121", "298 đường Cầu Diễn, Quận Bắc Từ Liêm, Thành phố Hà Nội"));
        addHost(new Host("ĐH Thủ đô Hà Nội", 1, "http://daihocthudo.edu.vn", "0438330708", "98, Dương Quảng Hàm, Quan Hoa, Cầu Giấy, Hà Nội"));
        addHost(new Host("HV Báo chí và Tuyên truyền", 1, "http://ajc.hcma.vn/", "0437546963", "36 Xuân Thủy - Cầu Giấy - Hà Nội"));
        addHost(new Host("HV Hậu cần", 1, "http://hocvienhaucan.edu.vn/", "02462680139", "Ngọc Thụy, Long Biên, Hà Nội"));
        addHost(new Host("ĐH Phương Đông", 1, "http://phuongdong.edu.vn/", "0437848513", "171 Trung Kính, Yên Hòa, Cầu Giấy, Hà Nội"));
        addHost(new Host("HV Cảnh sát nhân dân", 1, "http://www.hvcsnd.edu.vn", "840438362809", "Cổ Nhuế, Từ Liêm, Hà Nội"));

        addProfile(new Profile("default0", true, "default0"));

        addTest(new Test("Toán", "2017-06-22 14:20", Test.TestType.MultipleChoice, true));
        addTest(new Test("Ngữ văn", "2017-06-22 7:30", Test.TestType.Written, true));
        addTest(new Test("Ngoại ngữ", "2017-06-23 14:20", Test.TestType.MultipleChoice, false));
        addTest(new Test("Khoa học tự nhiên", "2017-06-23 7:30", Test.TestType.MultipleChoice, false));
        addTest(new Test("Khoa học xã hội", "2017-06-24 7:30", Test.TestType.MultipleChoice, false));
    }

    public static String curUser = null;

    public boolean Signin(String input) {
        if (input != null) {
            Profile attempt = getProfile(input);

            if (attempt != null) {
                curUser = input;
                return true;
            }
        }
        return false;
    }

    public boolean Signup(String input) {
        if (input != null) {
            Profile attempt = getProfile(input);

            if (attempt == null) {
                addProfile(new Profile(input, true, null));
                return true;
            }
        }
        return false;
    }

    public static void Logout() {
        curUser = null;
    }

    public Profile getCurProfile() {
        if (curUser != null)
            return getProfile(curUser);
        return null;
    }

    private void addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_NAME, profile.getUsername());
        values.put(PROFILE_HSCHOOL, profile.isHighschool());
        values.put(PROFILE_HOST, profile.getHost());

        db.insert(TABLE_PROFILE, null, values);
    }

    private Profile getProfile(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Profile profile = null;

        Cursor cursor = db.query(TABLE_PROFILE, new String[] {PROFILE_NAME, PROFILE_HSCHOOL, PROFILE_HOST}, PROFILE_NAME + "=?",
                new String[] {username}, null, null, null, null);
        if (cursor.moveToFirst()) {
            profile = new Profile(cursor.getString(0), (cursor.getString(1)).equals("1"), cursor.getString(2));
        }
        cursor.close();
        return profile;
    }

    public void addUnit(Unit unit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UNIT_NAME, unit.getName());
        values.put(UNIT_ID, unit.getId());

        db.insert(TABLE_UNIT, null, values);
    }

    public void addHost(Host host) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HOST_NAME, host.getName());
        values.put(HOST_UNIT, host.getUnit());
        values.put(HOST_ADDRESS, host.getAddress());
        values.put(HOST_PHONE, host.getPhone());
        values.put(HOST_WEB, host.getWebsite());

        db.insert(TABLE_HOST, null, values);
    }

    public ArrayList<String> getAllUsernames() {
        ArrayList<String> usernames = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + PROFILE_NAME + " FROM " + TABLE_PROFILE, null);

        if (cursor.moveToFirst()) {
            usernames = new ArrayList<>();
            do {
                usernames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return usernames;
    }

    private ArrayList<Unit> getAllUnits() {
        ArrayList<Unit> units = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_UNIT, null);

        if (cursor.moveToFirst()) {
            units = new ArrayList<>();
            do {
                units.add(new Unit(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return units;
    }

    private ArrayList<Host> getAllHostsInUnit(int unit) {
        ArrayList<Host> hosts = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOST + " WHERE " + HOST_UNIT + " = " + unit, null);

        if (cursor.moveToFirst()) {
            hosts = new ArrayList<>();
            do {
                hosts.add(new Host(cursor.getString(0), unit, cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return hosts;
    }

    private ArrayList<Test> getAllTests() {
        ArrayList<Test> tmp = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEST, null);

        if (cursor.moveToFirst()) {
            tmp = new ArrayList<>();
            do {
                tmp.add(new Test(cursor.getString(0), cursor.getString(1), Test.fromString(cursor.getString(2)), cursor.getString(3).equals("1")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tmp;
    }

    private void addSubject(Subject subject) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECT_NAME, subject.getName());
        values.put(SUBJECT_TEST, subject.getTest());

        db.insert(TABLE_SUBJECT, null, values);
    }

    private void addTest(Test test) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TEST_NAME, test.getName());
        values.put(TEST_DATETIME, test.getDatetime().toString());
        values.put(TEST_TYPE, Test.toString(test.getType()));
        values.put(TEST_COMP, test.isCompulsory());

        db.insert(TABLE_TEST, null, values);
    }

    private static ArrayList<Unit> curUnits = null;
    private static int curUnitChoice = 2;
    private static ArrayList<Host> curHosts = null;
    private static ArrayList<Test> tests = null;

    public ArrayList<Test> getTests() {
        if (tests == null)
            tests = getAllTests();
        ArrayList<Test> tmp = new ArrayList<>();
        tmp.addAll(tests);
        return tmp;
    }

    public ArrayList<Unit> getUnits() {
        if (curUnits == null)
            curUnits = getAllUnits();
        ArrayList<Unit> tmp = new ArrayList<>();
        tmp.addAll(curUnits);
        return tmp;
    }

    public ArrayList<Host> getHosts() {
        if (curHosts == null)
            curHosts = getAllHostsInUnit(curUnitChoice);
        ArrayList<Host> tmp = new ArrayList<>();
        tmp.addAll(curHosts);
        return tmp;
    }

    public void setUnitChoice(int unit) {
        if (curUnitChoice != unit) {
            curUnitChoice = unit;
            if (curHosts != null)
                curHosts.clear();
            curHosts = getAllHostsInUnit(unit);
        }
    }

    public int getUnitChoice() {
        return curUnitChoice;
    }

    public ArrayList<Location> getLocations() {
        ArrayList<Location> curLocs = null;
        if (curUser != null) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOCATION + " WHERE " + LOC_USER + "='" + curUser + "'", null);

            if (cursor.moveToFirst()) {
                curLocs = new ArrayList<>();
                do {
                    curLocs.add(new Location(cursor.getString(0), new LatLng(Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2))),
                            cursor.getString(3), cursor.getString(4), Location.fromString(cursor.getString(5))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return curLocs;
    }

    public String getHostChoice() {
        String s = null;
        if (curUser != null) {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + PROFILE_HOST + " FROM " + TABLE_PROFILE + " WHERE " + PROFILE_NAME + "='" + curUser + "'", null);

            if (cursor.moveToFirst()) {
                s = cursor.getString(0);
            }
            cursor.close();
        }
        return s;
    }

    public void updateProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILE_HOST, profile.getHost());
        values.put(PROFILE_HSCHOOL, profile.isHighschool());

        db.update(TABLE_PROFILE, values, PROFILE_NAME + " = ?", new String[] {profile.getUsername()});
    }

    public void updateTestChoices_Evil(boolean[] params) {
        if (params == null || params.length != 3 || curUser == null)
            return;

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TESTCHOICE, TC_USER + " = ?", new String[] {curUser});

        ArrayList<Test> t = getTests();

        for (int i = 0; i < t.size(); ++i) {
            if (i < 2 || params[i - 2]) {
                ContentValues values = new ContentValues();
                values.put(TC_USER, curUser);
                values.put(TC_TEST, t.get(i).getName());

                db.insert(TABLE_TESTCHOICE, null, values);
            }
        }
    }

    public void updateLocations_Evil(ArrayList<Location> locations) {
        if (locations == null || curUser == null)
            return;

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_LOCATION, LOC_USER + " = ?", new String[] {curUser});

        for (Location i : locations) {
            ContentValues values = new ContentValues();
            values.put(LOC_USER, curUser);
            values.put(LOC_NAME, i.getName());
            values.put(LOC_LAT, i.getPos().latitude);
            values.put(LOC_LONG, i.getPos().longitude);
            values.put(LOC_TYPE, Location.toString(i.getType()));
            values.put(LOC_ADDRESS, i.getAddress());

            db.insert(TABLE_LOCATION, null, values);
        }
    }
}
