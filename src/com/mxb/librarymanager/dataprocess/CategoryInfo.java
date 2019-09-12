package com.mxb.librarymanager.dataprocess;

public class CategoryInfo {
    public static final String NULL_CATEGORY_NAME = "<空类别>";
    public String name;
    public int bookCount;

    public String toUI() {
        return name.equals("") ? NULL_CATEGORY_NAME : name;
    }

    public static String toUI(String category) {
        return category.equals("") ? NULL_CATEGORY_NAME : category;
    }

    public String toData() {
        return name.equals(NULL_CATEGORY_NAME) ? "" : name;
    }

    public boolean isNullCategory() {
        return name.equals("") || name.equals(NULL_CATEGORY_NAME);
    }
}
