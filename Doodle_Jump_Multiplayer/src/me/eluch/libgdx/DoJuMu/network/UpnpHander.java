package me.eluch.libgdx.DoJuMu.network;

import java.io.IOException;
import java.net.InetAddress;

import javax.xml.parsers.ParserConfigurationException;

import me.eluch.libgdx.DoJuMu.Options;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.xml.sax.SAXException;

public class UpnpHander {

	private static GatewayDiscover discover;
	private static GatewayDevice d;
	private static InetAddress localAddress;
	private static PortMappingEntry portMapping;

	private static void preWork() {
		discover = new GatewayDiscover();
		try {
			discover.discover();
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		d = discover.getValidGateway();

		if (d != null) {
			System.out.println("Gateway device found");
		} else {
			System.out.println("No valid gateway device found.");
			return;
		}

		localAddress = d.getLocalAddress();

		portMapping = new PortMappingEntry();
	}

	public static void map() {
		new Thread(() -> {
			preWork();
			try {
				if (!d.getSpecificPortMappingEntry(Options.SERVER_PORT, "TCP", portMapping)) {
					if (d.addPortMapping(Options.SERVER_PORT, Options.SERVER_PORT, localAddress.getHostAddress(), "TCP", "DoJuMu TCP")) {
						System.out.println("TCP port mapping successful!");
					}
				} else {
					System.out.println("TCP port has already mapped!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (!d.getSpecificPortMappingEntry(Options.SERVER_PORT, "UDP", portMapping)) {
					if (d.addPortMapping(Options.SERVER_PORT, Options.SERVER_PORT, localAddress.getHostAddress(), "UDP", "DoJuMu UDP")) {
						System.out.println("UDP port mapping succesful!");
					}
				} else {
					System.out.println("UDP port has already mapped!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public static void unmap() {
		new Thread(() -> {
			preWork();
			try {
				if (d.getSpecificPortMappingEntry(Options.SERVER_PORT, "TCP", portMapping)) {
					d.deletePortMapping(Options.SERVER_PORT, "TCP");
					System.out.println("TCP port unmapped");
				}
			} catch (IOException | SAXException e) {
				e.printStackTrace();
			}
			try {
				if (d.getSpecificPortMappingEntry(Options.SERVER_PORT, "UDP", portMapping)) {
					d.deletePortMapping(Options.SERVER_PORT, "UDP");
					System.out.println("UDP port unmapped");
				}
			} catch (IOException | SAXException e) {
				e.printStackTrace();
			}
		}).start();
	}

}
