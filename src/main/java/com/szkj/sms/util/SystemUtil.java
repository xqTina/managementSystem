package com.szkj.sms.util;

import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/27 9:38
 */
public class SystemUtil {
    /**
     * 获取服务器IP地址
     *
     * @return
     */
    public static String getServerIp() {
        String SERVER_IP = null;
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                ip = (InetAddress) ni.getInetAddresses().nextElement();
                SERVER_IP = ip.getHostAddress();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {
                    SERVER_IP = ip.getHostAddress();
                    break;
                } else {
                    ip = null;
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            LoggerFactory.getLogger(SystemUtil.class).error(e.getLocalizedMessage());
        }

        return SERVER_IP;
    }

    //得到计算机的ip,名称,操作系统名称,操作系统版本
    public static void Config() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress().toString(); //获取本机ip
            String hostName = addr.getHostName().toString(); //获取本机计算机名称
            System.out.println("本机IP：" + ip + "\n本机名称:" + hostName);
            Properties props = System.getProperties();
            System.out.println("操作系统的名称：" + props.getProperty("os.name"));
            System.out.println("操作系统的版本：" + props.getProperty("os.version"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
