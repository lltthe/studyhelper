package com.example.lamlethanhthe.studyhelper.DataModules;

public class Test {
    public enum TestType {Written, MultipleChoice, Both}

    private String name;
    private String datetime;
    private TestType type;
    private boolean compulsory;

    public Test(String name, String datetime, TestType type, boolean compulsory) {
        this.name = name;
        this.datetime = datetime;
        this.type = type;
        this.compulsory = compulsory;
    }

    public String getName() {
        return name;
    }

    public String getDatetime() {
        return datetime;
    }

    public TestType getType() {
        return type;
    }

    public boolean isCompulsory() {
        return compulsory;
    }

    public static String toString(TestType t) {
        if (t == TestType.Written)
            return "Tự luận";
        if (t == TestType.MultipleChoice)
            return "Trắc nghiệm";
        return "Hỗn hợp";
    }

    public static TestType fromString(String s) {
        if (s.equals("Tự luận"))
            return TestType.Written;
        if (s.equals("Trắc nghiệm"))
            return TestType.MultipleChoice;
        return TestType.Both;
    }
}