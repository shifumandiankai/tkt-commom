package com.mtl.cypw.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tang.
 * @date 2019/12/10.
 */
@Slf4j
@Component
public class FileUtil {

    /**
     * InputStream 转 File
     *
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            log.error("文件转换失败", e);
        }
    }

    public synchronized static File base64StringToImage(String base64String) {
        BASE64Decoder decoder = new BASE64Decoder();
        File f1 = new File("temp.jpg");
        try {
            String baseValue = base64String.replaceAll(" ", "+");
            int index = baseValue.indexOf("base64,");
            if (index > -1) {
                baseValue = baseValue.substring(index + 7);
            }
            byte[] b = decoder.decodeBuffer(baseValue);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(f1);
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("文件转换失败", e);
        }
        return f1;
    }

    public static String imageToBase64Str(File file) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            log.error("文件转换失败", e);
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    public static String getFileName(String mimeType) {
        int randomNum = (int) (Math.random() * 900 + 100);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date())).append(randomNum).append(mimeType);
        return sb.toString();
    }

    private static final QRCodeWriter QR_CODE_WRITER = new QRCodeWriter();

    /**
     * 生成二维码图片
     *
     * @param text   内容
     * @param format 图片格式
     * @param width  宽
     * @param height 高
     * @return
     */
    public static byte[] generateQrCode(String text, String format, int width, int height) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            BitMatrix bitMatrix = QR_CODE_WRITER.encode(text, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToStream(bitMatrix, format, os);
            return os.toByteArray();
        } catch (WriterException e) {
            log.error("二维码生成异常", e);
        } catch (IOException e) {
            log.error("二维码生成异常", e);
        }
        return null;
    }

    /**
     * 生成二维码图片且转为base64格式
     *
     * @param text   内容
     * @param format 图片格式
     * @param width  宽
     * @param height 高
     * @return
     */
    public static String generateQrCodeToBase64(String text, String format, int width, int height) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(generateQrCode(text, format, width, height));
    }

    /**
     * 给二维码添加logo
     */
    public static BufferedImage addLogo(File qrCode, File logoPic) {
        try {
            if (!logoPic.isFile()) {
                throw new IOException("file not find !");
            }
            BufferedImage barCodeImage = ImageIO.read(qrCode);
            //读取Logo图片
            BufferedImage logo = ImageIO.read(logoPic);

            // 读取二维码图片，并构建绘图对象
            Graphics2D g = barCodeImage.createGraphics();

            int widthLogo = barCodeImage.getWidth() / 6;
            int heightLogo = barCodeImage.getWidth() / 6;

            // 计算图片放置位置
            int x = (barCodeImage.getWidth() - widthLogo) / 2;
            int y = (barCodeImage.getHeight() - heightLogo) / 2;

            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 0, 0);
            g.setStroke(new BasicStroke(10));
            g.setColor(Color.WHITE);
            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();
            return barCodeImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给二维码添加logo
     */
    public static String addLogoToBase64(File qrCode, File logoPic) {
        BufferedImage image = addLogo(qrCode, logoPic);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            log.error("图片输出失败", e);
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(os.toByteArray());
    }

    public static void main(String[] args) throws IOException {
        String str = generateQrCodeToBase64("abcdefg12345678", "jpg", 540, 540);
        File f = base64StringToImage(str);
        File log = new File("D:\\Users\\Pictures\\Saved Pictures\\彩熠logo.png");
        BufferedImage image = addLogo(f, log);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        BASE64Encoder encoder = new BASE64Encoder();
        String s = encoder.encode(os.toByteArray());
        base64StringToImage(s);
    }


}
