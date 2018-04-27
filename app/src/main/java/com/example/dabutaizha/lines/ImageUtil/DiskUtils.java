package com.example.dabutaizha.lines.ImageUtil;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.nio.file.Path;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/1/22 下午5:56.
 */

public class DiskUtils {

    public static long getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(path.getPath());
        long availableBlock = statFs.getAvailableBlocks();
        long blockSize = statFs.getBlockSize();
        return availableBlock * blockSize;
    }

}
