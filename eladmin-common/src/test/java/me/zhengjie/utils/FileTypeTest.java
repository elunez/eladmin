package me.zhengjie.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTypeTest {

    @ParameterizedTest
    @CsvSource({
            "jpg, 图片", "jpeg, 图片", "png, 图片", "gif, 图片", "tif, 图片",
            "txt, 文档", "pdf, 文档", "doc, 文档", "docx, 文档", "xls, 文档", "xlsx, 文档",
            "mp3, 音乐", "wav, 音乐", "m4a, 音乐",
            "mp4, 视频", "webm, 视频", "ogg, 视频"
    })
    public void classifiesSupportedExtensions(String extension, String expectedType) {
        assertEquals(expectedType, FileUtil.getFileType(extension));
    }

    @ParameterizedTest
    @CsvSource({
            "JPG, 图片", "JpEg, 图片", "PDF, 文档", "Mp3, 音乐", "MP4, 视频"
    })
    public void classifiesExtensionsRegardlessOfCase(String extension, String expectedType) {
        assertEquals(expectedType, FileUtil.getFileType(extension));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"j", "jp", "peg", "xl", "oc", "p3", "v", "jpg png", "doc pdf", " ", ".jpg", "zip"})
    public void rejectsMissingOrUnsupportedExtensions(String extension) {
        assertEquals(FileUtil.OTHER, FileUtil.getFileType(extension));
    }

    @ParameterizedTest
    @CsvSource({"GIF, 图片", "TIF, 图片", "AIF, 音乐", "AVI, 视频"})
    public void classifiesUppercaseExtensionsInTurkishLocale(String extension, String expectedType) {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(new Locale("tr", "TR"));
            assertEquals(expectedType, FileUtil.getFileType(extension));
        } finally {
            Locale.setDefault(originalLocale);
        }
    }
}
