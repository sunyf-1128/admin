package com.baizhi.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class ImageCode {
    private int w = 70;
    private int h = 35;
    private Random r = new Random();
    private String[] fontNames = {"宋体", "华文楷体", "黑体", "华文隶书", "微软雅黑", "楷体_GB2312"};
    private String codes = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHIJKMNPQRSTUVWXYZ";
    private Color bgColor = new Color(255, 255, 255);
    private String text;

    private Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);
        int size = r.nextInt(5) + 24;
        return new Font(fontName, style, size);
    }

    private void drawLine(BufferedImage image) {
        int num = 3;
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(w);
            int y1 = r.nextInt(h);
            int x2 = r.nextInt(w);
            int y2 = r.nextInt(h);
            g2.setStroke(new BasicStroke(1.5F));//设置线条的粗细
            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, w, h);
        return image;
    }

    public BufferedImage getImage() {
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";
            sb.append(s);
            float x = i * 1.0F * w / 4;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, x, h - 5);
        }
        this.text = sb.toString();
        drawLine(image);
        return image;
    }

    public String getText() {
        return text;
    }

    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }

    public static void main(String[] args) throws IOException {
        ImageCode vc = new ImageCode();
        BufferedImage bi = vc.getImage();


        ImageCode.output(bi, new FileOutputStream("codeImage/xxx.jpg"));


        System.out.println(vc.getText());
    }
/*
*  ------使用-----
 * 1. 生成图片
 * 2. 保存图片上的文本到session域中
 * 3. 把图片响应给客户端
 *
ImageCode vc = new ImageCode();
    BufferedImage image = vc.getImage();
        request.getSession().setAttribute("code", vc.getText());//保存图片上的文本到session域
        ImageCode.output(image, response.getOutputStream());
*
*
* */
}
