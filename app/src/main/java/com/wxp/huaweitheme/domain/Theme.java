package com.wxp.huaweitheme.domain;

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/11/03
 *     desc   :
 * </pre>
 */
public class Theme {

    private String themeName;
    private String previewIcons;
    private String previewUnlock;
    private String themeSize;

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getPreviewIcons() {
        return previewIcons;
    }

    public void setPreviewIcons(String previewIcons) {
        this.previewIcons = previewIcons;
    }

    public String getPreviewUnlock() {
        return previewUnlock;
    }

    public void setPreviewUnlock(String previewUnlock) {
        this.previewUnlock = previewUnlock;
    }

    public String getThemeSize() {
        return themeSize;
    }

    public void setThemeSize(String themeSize) {
        this.themeSize = themeSize;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "themeName='" + themeName + '\'' +
                ", previewIcons='" + previewIcons + '\'' +
                ", previewUnlock='" + previewUnlock + '\'' +
                ", themeSize='" + themeSize + '\'' +
                '}';
    }
}
