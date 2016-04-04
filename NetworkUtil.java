package com.atom8.app.util;

import android.net.DhcpInfo;
import android.util.Log;

import com.atom8.app.SplashActivity;
import com.google.api.client.util.Lists;
import com.google.common.net.InetAddresses;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by thehellmaker on 4/4/2016.
 */
public class NetworkUtil {

    public static int getNetworkSize(DhcpInfo dhcpInfo) throws UnknownHostException {

        byte[] bytes = BigInteger.valueOf(~dhcpInfo.netmask).toByteArray();
        InetAddress address = InetAddresses.fromLittleEndianByteArray(bytes);
        Log.i(Atom8NetworkUtil.class.getName(), address.getHostAddress());
        String[] ipAddrParts = StringUtils.split(address.getHostAddress(), ".");
        int count = 1;
        for (String ipPart : ipAddrParts) {
            Integer ipPartInt = Integer.parseInt(ipPart)+1;
            if (ipPartInt != 0) count *= ipPartInt;
        }
        return count;
    }

    public static List<InetAddress> getAllIPAddresses(DhcpInfo dhcpInfo) throws UnknownHostException {
        // One is subtracted as gateway starts from 0.1 and not 0.0
        int size = Atom8NetworkUtil.getNetworkSize(dhcpInfo)-1;
        int gateway = dhcpInfo.gateway;
        byte[] bytes = BigInteger.valueOf(gateway).toByteArray();
        InetAddress address = InetAddresses.fromLittleEndianByteArray(bytes);
        List<InetAddress> allIpAddresses = Lists.newArrayList();
        for(int i = 0; i < size; i++) {
            Log.e(SplashActivity.class.getName(), address.getHostAddress());
            allIpAddresses.add(address);
            address = InetAddresses.increment(address);
        }
        return allIpAddresses;
    }


}
