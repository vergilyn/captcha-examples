package com.vergilyn.examples.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 传统的图片验证码
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/30
 */
public class ImageCaptcha {
    /** 图片的宽度 */
    private int width = 160;
    /** 图片的高度 */
    private int height = 40;
    /** 验证码字符个数 */
    private int codeCount = 4;
    /** 验证码干扰线数 */
    private int lineCount = 20;
    /** 验证码 */
    private String code = null;
    /** 验证码字符
     *  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
     */
    private String character = "1234567890";
    // 验证码图片Buffer
    private BufferedImage buffImg = null;

    Random random = new Random();

    public ImageCaptcha() {
        createImage();
    }

    /**
     * @param width 图片宽度
     * @param height 图片高度
     */
    public ImageCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
        createImage();
    }

    /**
     * @param width 图片宽度
     * @param height 图片高度
     * @param codeCount 验证码字符个数
     */
    public ImageCaptcha(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        createImage();
    }

    /**
     * @param width 图片宽度
     * @param height 图片高度
     * @param codeCount 验证码字符个数
     * @param lineCount 干扰线数
     */
    public ImageCaptcha(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        createImage();
    }

    // 生成图片
    private void createImage() {
        int fontWidth = width / codeCount;// 字体的宽度
        int fontHeight = height - 5;// 字体的高度
        int codeY = height - 8;

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.getGraphics();
        // Graphics2D g = buffImg.createGraphics();
        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设置字体
        Font font = getFont(fontHeight);
        // Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点
        float yawpRate = 0.01f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            buffImg.setRGB(x, y, random.nextInt(255));
        }

        String str1 = randomStr(codeCount);// 得到随机字符
        this.code = str1;
        for (int i = 0; i < codeCount; i++) {
            String strRand = str1.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            // g.drawString(a,x,y);
            // a为要画出来的东西，x和y表示要画的东西最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处

            g.drawString(strRand, i * fontWidth + 3, codeY);
        }

    }

    // 得到随机字符
    private String randomStr(int n) {
        StringBuilder randomStr = new StringBuilder();
        int len = character.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            randomStr.append(character.charAt((int) r));
        }
        return randomStr.toString();
    }

    // 得到随机颜色
    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     */
    private Font getFont(int size) {
        Font font[] = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, size);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        font[2] = new Font("Fixedsys", Font.PLAIN, size);
        font[3] = new Font("Wide Latin", Font.PLAIN, size);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
        return font[random.nextInt(5)];
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        try {
            sos.flush();
        } catch (Exception e) {
        }finally {
            sos.close();
        }
        sos.close();
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code.toLowerCase();
    }

    // 使用方法
    /*
     * public void getCode3(HttpServletRequest req, HttpServletResponse
     * response,HttpSession session) throws IOException{ // 设置响应的类型格式为图片格式
     * response.setContentType("image/jpeg"); //禁止图像缓存。
     * response.setHeader("Pragma", "no-cache");
     * response.setHeader("Cache-Control", "no-cache");
     * response.setDateHeader("Expires", 0);
     * 
     * 
     * CreateImageCode vCode = new CreateImageCode(100,30,5,10);
     * session.setAttribute("code", vCode.getCode());
     * vCode.write(response.getOutputStream()); }
     */
    /*public static void main(String[] args) {
        ValidateCodeUtil tCode = new ValidateCodeUtil(120, 40, 5, 100);
        try {
            String path = "D:/test/" + new Date().getTime() + ".png";
            System.out.println(tCode.getCode() + " >" + path);
            OutputStream sos = new FileOutputStream(path);
            tCode.write(sos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}