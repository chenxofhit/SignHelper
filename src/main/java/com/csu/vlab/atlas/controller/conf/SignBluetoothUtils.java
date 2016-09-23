package com.csu.vlab.atlas.controller.conf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.csu.vlab.atlas.common.constants.AtlasPropertyConfigurer;

/**
 * ClassName:SignBluetoothUtils <br/>
 * Date: Apr 9, 2016 11:15:06 AM <br/>
 * 
 * @author chenx
 * @version
 * @since JDK 1.7
 * @see
 */

public class SignBluetoothUtils {

	@Autowired
	public AtlasPropertyConfigurer atlasPropertyConfigurer;

	public String getUploadList() throws Exception {

		StringBuilder sb = new StringBuilder();

		String nearblooth = atlasPropertyConfigurer
				.getProperty("nearbluetooth");
		String blooth216 = atlasPropertyConfigurer.getProperty("216");

		if (null == blooth216) {
			blooth216 = "10:2A:B3:5E:26:15";
		}

		List<String> uploadlist = new ArrayList<String>();
		uploadlist.add(blooth216);

		if (null != nearblooth && null != blooth216) {
			String[] nears = nearblooth.split(",");
			boolean r[] = new boolean[nears.length];
			Random random = new Random();

			int m = (int) (Math.random() * nears.length + 1);

			int n = 0;
			while (n!=m) {
				int temp = random.nextInt(nears.length);

				if (!r[temp]) {
					n++;
					uploadlist.add(nears[temp]);
					r[temp] = true;
				}
			}
		}

		Collections.shuffle(uploadlist);
		Iterator<String> iterator = uploadlist.iterator();

		while (iterator.hasNext()) {
			Thread.sleep(7 * ((int) (Math.random() * 2 + 1)));
			int rssi = (int) Math.floor(-100 * Math.random());
			String iteratorStr = String.valueOf((new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss")).format(new Date()))
					+ "\n"
					+ iterator.next() + "\n" + String.valueOf(rssi) + "\n";
			sb.append(iteratorStr);
		}
		return sb.toString();
	}

	public String sign(String mac, String uploadlist) throws IOException {
		Socket clientSocket = null;
		InputStreamReader streamReader = null;
		BufferedReader reader = null;
		PrintWriter out = null;
		try {
			clientSocket = new Socket("202.197.61.249", 8887);

			int timeout = 10 * 1000;
			clientSocket.setSoTimeout(timeout);
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					clientSocket.getOutputStream(), "UTF-8")), true);

			streamReader = new InputStreamReader(clientSocket.getInputStream());
			reader = new BufferedReader(streamReader);

			StringBuilder stringbuilder = new StringBuilder("upload\n");
			stringbuilder.append((new StringBuilder(mac).append("\n")
					.toString()));

			// String uploadlist = this.getUploadList();
			stringbuilder.append(uploadlist).append("\n").toString();
			stringbuilder.append("exit");

			out.println(stringbuilder);
			out.flush();
			clientSocket.shutdownOutput();

			char chars[] = new char[64];
			int len;

			StringBuffer result = new StringBuffer();
			while ((len = reader.read(chars)) != -1) {
				result.append(new String(chars, 0, len));
			}

			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		} finally {
			if (null != streamReader)
				streamReader.close();
			if (null != reader)
				reader.close();
			if (null != out)
				out.close();
			if (null != clientSocket)
				clientSocket.close();
		}
	}
}