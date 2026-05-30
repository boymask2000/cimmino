package com.cimmino.shop.service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class NETService {
	public Map<String, Object> runDiagnostics() throws Exception {

		Map<String, Object> result = new LinkedHashMap<>();

		result.put("interfaces", listInterfaces());
		result.put("localIps", listLocalIps());
		result.put("multicastSupported", checkMulticastSupport());
		result.put("udpTest", udpLoopbackTest());
		result.put("dns", dnsTest());

		return result;
	}

	private List<String> listInterfaces() throws Exception {

		List<String> list = new ArrayList<>();

		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

		while (interfaces.hasMoreElements()) {

			NetworkInterface ni = interfaces.nextElement();

			list.add(ni.getName() + " | up=" + ni.isUp() + " | loopback=" + ni.isLoopback() + " | multicast="
					+ ni.supportsMulticast());
		}

		return list;
	}

	private List<String> listLocalIps() throws Exception {

		List<String> ips = new ArrayList<>();

		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

		while (interfaces.hasMoreElements()) {

			NetworkInterface ni = interfaces.nextElement();

			Enumeration<InetAddress> addresses = ni.getInetAddresses();

			while (addresses.hasMoreElements()) {

				InetAddress addr = addresses.nextElement();

				if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {

					ips.add(addr.getHostAddress());
				}
			}
		}

		return ips;
	}

	private boolean checkMulticastSupport() throws Exception {

		for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {

			if (ni.isUp() && ni.supportsMulticast()) {
				return true;
			}
		}

		return false;
	}

	private String udpLoopbackTest() {

		try {

			int port = 19000;

			Thread receiver = new Thread(() -> {

				try (DatagramSocket socket = new DatagramSocket(port)) {

					socket.setSoTimeout(3000);

					byte[] buffer = new byte[1024];

					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

					socket.receive(packet);
				} catch (Exception ignored) {
				}
			});

			receiver.start();

			Thread.sleep(300);

			DatagramSocket socket = new DatagramSocket();
			socket.setBroadcast(true);

			byte[] data = "UDP_TEST".getBytes();

			DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"),
					port);

			socket.send(packet);

			receiver.join(3000);

			return "OK";

		} catch (Exception e) {

			return "FAIL: " + e.getMessage();
		}
	}

	private String dnsTest() {

		try {

			InetAddress addr = InetAddress.getLocalHost();

			return addr.getHostAddress();

		} catch (Exception e) {

			return "FAIL";
		}
	}

}
