package com.cimmino.shop.controller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cimmino.shop.Master;
import com.cimmino.shop.service.AllineamentoService;

@Controller
@RequestMapping("/net")
public class IpController {
	@Autowired
	AllineamentoService allineamentoService;

	@GetMapping("/myip")
	public String myIp(Model model) {

		String ip = getLocalNetworkIp();

		model.addAttribute("ip", ip);

		return "myip";
	}

	@GetMapping("/getMasterAddress")
	public String getMasterAddress(Model model) {

		Master master = new Master();

		model.addAttribute("master", master);

		return "getMasterAddress";
	}

	@PostMapping("/allineamento")
	public String allineamento( //
			@ModelAttribute("master") Master master, //
			Model model) {

		allineamentoService.start(master);
		
		
		return "getMasterAddress";
	}

	private String getLocalNetworkIp() {
		String out = "";
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {

				NetworkInterface ni = interfaces.nextElement();

				// scarta interfacce inutili
				if (ni.isLoopback() || !ni.isUp() || ni.isVirtual()) {
					continue;
				}

				Enumeration<InetAddress> addresses = ni.getInetAddresses();
				while (addresses.hasMoreElements()) {

					InetAddress addr = addresses.nextElement();

					String ip = addr.getHostAddress();
					if (ip.contains(":") || ip.startsWith("127") || ip.startsWith("172"))
						continue;

					out += " - " + ip;

				}
//				while (addresses.hasMoreElements()) {
//
//					InetAddress addr = addresses.nextElement();
//
//					String ip = addr.getHostAddress();
//System.out.println(ip);
//					// prendi solo IPv4 (tipico in LAN)
//					if (ip.contains(".") && !ip.equals("127.0.0.1")) {
//						return ip;
//					}
//				}
			}
			return out;

		} catch (Exception e) {
			return "Errore nel recupero IP";
		}

		// return "IP non trovato";
	}
}