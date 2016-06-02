package com.vzone.tmdsdk.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络相关工具方法
 * @author iTant
 *
 */
public class NetworkTool {

	/**
	 * 判断是否有网络
	 * 
	 * @param context 上下文
	 * @return true:表示有网络 false:表示没有网络
	 */
	public static boolean isNetworkConnected(Context context) {
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;
	}
	
	/**
	 * 判断是否已经连接上WiFi
	 * 
	 * @param context 上下文
	 * @return true:表示已连接WiFi false:表示没有连接WiFi
	 */
	public static boolean isWiFiConnected(Context context) {
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {
	            return (mNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI);  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * 判断是否连接了移动网络
	 * 
	 * @param context 上下文
	 * @return true:表示连接了移动网络 false:表示没有连接移动网络
	 */
	public static boolean isMobileConnected(Context context) {
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {
	            return (mNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE);  
	        }  
	    }  
	    return false;  
	}
	
	/**
	 * 获取手机的IP地址
	 * 
	 * @return 有IP的话返回IP，否则返回null
	 */
	public static String getLocalIpAddress() {

		try {
			Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaceEnumeration.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
				Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
				while (inetAddressEnumeration.hasMoreElements()) {
					InetAddress inetAddress = inetAddressEnumeration.nextElement();
					if (!inetAddress.isLoopbackAddress() && isIPv4Address(inetAddress.getHostAddress())) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			
		}

		return null;
	}
	
	/**
	 * 判断一个字符串是否IPv4形式的IP地址
	 * 
	 * @param unknownAddress 待判定的字符串
	 * @return true:表示该字符串是IP地址 false:表示该字符串不是IP地址
	 */
	public static boolean isIPv4Address(String unknownAddress) {
		String regex = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(unknownAddress);
		while (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @return 运营商名称
	 */
	public static String getCommunicationCorporation(Context context) {
		// 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）
		// （也称为“MCC / MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
		String corporation = "未知";
		String IMSI =  ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
		System.out.println(IMSI);
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
			corporation = "中国移动";
		} else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
			corporation = "中国联通";
		} else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
			corporation = "中国电信";
		}
		return corporation;
	}

	/**
	 * 获取外网IP
	 * @return
     */
	public static String getOutIP() {
        String IP = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";

            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setUseCaches(false);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    IP = data.getString("ip");
                } else {
                    IP = "";
                }
            } else {
                IP = "";
            }
        } catch (Exception e) {
            IP = "";
        }
        return IP;
	}
}