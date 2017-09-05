package com.aoao.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by yang on 2017/5/24.
 */
public class RunShellUtils {

    public static String getVideoSize(String filepath){
        System.out.println("videoSize_filepath:"+filepath);
        //export LD_LIBRARY_PATH=/usr/local/ffmpeg/lib/:/usr/local/lib;
        String cmd = "/usr/local/ffmpeg/bin/ffprobe -i " + filepath + " -show_streams 2>&1 | grep \"width\\|height\\|rotation\" | grep -v coded | awk -F\"=\" '{a[$1]=$2}END{if(length(a[\"rotation\"])>0){print a[\"height\"]\"x\"a[\"width\"]} else {print a[\"width\"]\"x\"a[\"height\"]}}'";
//        String cmd = "/usr/local/ffmpeg/bin/ffprobe -i " + filepath + " -show_streams 2>&1 | grep \"width\\|height\" | grep -v coded | awk -F\"=\" '{a[$1]=$2}END{print a[\"width\"]\"x\"a[\"height\"]}'";
        System.out.println("videoSize_filepath:"+filepath);
        return getRunShellResult(cmd);
    }
    public static String getVideoCut(String filepath, String newFile, String start, String duration){
        System.out.println("filepath:" + filepath);
        String cmd = "/usr/local/ffmpeg/bin/ffmpeg -ss " + start + " -i  " + filepath + " -vcodec copy -acodec copy  -t " + duration + " " + newFile + " -y -v quiet";
        System.out.println("newFile:" + newFile);
        return getRunShellResult(cmd);
    }

    public static String getFileSize(String filepath){
        System.out.println("fileSize_filepath:"+filepath);
        String cmd = "/usr/bin/ls -l " + filepath + " | awk '{print $5}'";
        System.out.println("fileSize_filepath:"+filepath);
        return getRunShellResult(cmd);
    }

    public static String getCover(String videoFilepath,String outfilepath){
        ///usr/local/ffmpeg/bin/ffmpeg -i output.mp4 -y -loglevel quiet -f image2 -ss 37.050 -t 0.001 -s 352x240 b.jpg
        System.out.println("videoFilepath:"+videoFilepath+"|outfilepath="+outfilepath);
        String cmd = "/usr/local/ffmpeg/bin/ffmpeg -i " + videoFilepath + " -y -loglevel quiet -f image2 -ss 10.050 -t 0.001 "+ outfilepath;
        System.out.println("outfilepath:"+outfilepath);
        return getRunShellResult(cmd);
    }
    public static String getDuration(String filepath){
        System.out.println("fileSize_duration:"+filepath);
        //export LD_LIBRARY_PATH=/usr/local/ffmpeg/lib/:/usr/local/lib;
        String cmd = "/usr/local/ffmpeg/bin/ffprobe -i " + filepath + " -show_format 2>&1 | grep duration | sed -n 's/duration=//p'";
        System.out.println("fileSize_duration:"+cmd);
        String old_duration = getRunShellResult(cmd);
        if (!StringUtil.isEmpty(old_duration)){
            if (old_duration.contains(".")){
                return old_duration.substring(0,old_duration.indexOf("."));
            }else{
                return old_duration;
            }
        }else{
            return "0";
        }
    }
    private static String getRunShellResult(String cmd){
        System.out.println("cmd:"+cmd);
        Process ps;
        String result = "";
        try {

            ps = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",cmd},null,null);
            BufferedInputStream in = new BufferedInputStream (ps.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String lineStr;
            while ((lineStr = br.readLine()) != null) {
                result = lineStr;
            }
            System.out.println("result:"+result);
            // 关闭输入流
            br.close();
            in.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
