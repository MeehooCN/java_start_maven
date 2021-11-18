package com.meehoo.biz.common.util;

//import it.sauronsoftware.jave.AudioAttributes;
//import it.sauronsoftware.jave.Encoder;
//import it.sauronsoftware.jave.EncoderException;
//import it.sauronsoftware.jave.EncodingAttributes;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 视频音频文件处理
 * Created by CZ on 2018/9/18.
 */
public class JavaCVUtil {

//    private static final Java2DFrameConverter frame2ImageConverter = new Java2DFrameConverter();
//    private static final String IMGFORMAT_JPG = "jpg";
//
//    /**
//     * 截取视频缩略图
//     *
//     * @param videoInputStream 视频输入流
//     * @param targetFrame      截取的帧位,如果该值超过了视频的最大帧位，则截取视频的中间帧
//     * @param targetWidth      图片压缩宽度
//     * @param targetHeight     图片压缩高度
//     * @return
//     */
//    public static byte[] grabImageToByteArray(InputStream videoInputStream, int targetFrame, int targetWidth, int targetHeight) {
//        try {
//            FFmpegFrameGrabber ffmpegGrabber = new FFmpegFrameGrabber(videoInputStream);
//            ffmpegGrabber.start();
//            int lengthInFrames = ffmpegGrabber.getLengthInFrames();
//            targetFrame = lengthInFrames < targetFrame ? lengthInFrames / 2 : targetFrame;
//            Frame grabbedFrame = null;
//            for (int i = 0; i < targetFrame; i++) {
//                grabbedFrame = ffmpegGrabber.grabImage();
//            }
//
//            //图片压缩
//            if (grabbedFrame != null) {
//                BufferedImage srcImage = frame2ImageConverter.getBufferedImage(grabbedFrame);
//                BufferedImage scaledImage = compressImage(srcImage, targetWidth, targetHeight);
//                try (ByteArrayOutputStream bufferedImageOutputStream = new ByteArrayOutputStream()) {
//                    ImageIO.write(scaledImage, IMGFORMAT_JPG, bufferedImageOutputStream);
//                    return bufferedImageOutputStream.toByteArray();
//                } finally {
//                    ffmpegGrabber.stop();
//                    videoInputStream.close();
//                }
//            }
//            ffmpegGrabber.stop();
//            videoInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static BufferedImage compressImage(BufferedImage srcImage, int targetWidth, int targetHeight) {
//        int[] adaptedRes = adaptWidthAndHeight(srcImage.getWidth(), srcImage.getHeight(), targetWidth, targetHeight);
//        int scaleWidth = adaptedRes[0];
//        int scaleHeight = adaptedRes[1];
//        BufferedImage scaledImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);
//        scaledImage.getGraphics().drawImage(srcImage.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_SMOOTH),
//                0, 0, scaleWidth, scaleHeight, null);
//        return scaledImage;
//    }
//
//    private static int[] adaptWidthAndHeight(int srcWidth, int srcHeight, int width, int height) {
//        int[] result = new int[2];
//        double scale = srcWidth < srcHeight ? (double) srcWidth / srcHeight : (double) srcHeight / srcWidth;
//        if (width < srcWidth || height < srcHeight) {
//            double tempScale1 = (double) width / srcWidth;
//            double tempScale2 = (double) height / srcHeight;
//            scale = scale < tempScale1 ? (scale < tempScale2 ? scale : tempScale2) : tempScale1;
//        }
//        result[0] = (int) (scale * srcWidth);
//        result[1] = (int) (scale * srcHeight);
//        return result;
//    }
//
//    /**
//     * 将音频文件转换成mp3格式
//     *
//     * @param audioFile amr文件
//     * @param mp3File   mp3文件
//     * @return {@code true}转换成功/{@code false}转换失败
//     */
//    public static boolean convertAudioFileToMp3(File audioFile, File mp3File) {
//        AudioAttributes audioAttrs = new AudioAttributes();
//        audioAttrs.setCodec("libmp3lame");
//        EncodingAttributes encodingAttrs = new EncodingAttributes();
//        encodingAttrs.setFormat("mp3");
//        encodingAttrs.setAudioAttributes(audioAttrs);
//        try {
//            Encoder encoder = new Encoder();
//            encoder.encode(audioFile, mp3File, encodingAttrs);
//            return true;
//        } catch (EncoderException ignored) {
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//    }

}
