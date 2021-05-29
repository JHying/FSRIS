package tw.ym.fshra.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// GZIP 壓縮、解壓縮工具類
public class GZIPUtil {

	/**
	 * 壓縮字串
	 * @param str 字串
	 * @return 壓縮後字符陣列
	 */
	public static byte[] compress(final String str) {
		if ((str == null) || (str.length() == 0)) {
			throw new IllegalArgumentException("Cannot zip null or empty string");
		}

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
				gzipOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
			}
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Failed to zip content", e);
		}
	}

	// 解壓縮字串
	/**
	 * 解壓字串
	 * @param compressed 壓縮字符陣列
	 * @return 解壓後字串
	 */
	public static String decompress(byte[] compressed) {
		if ((compressed == null) || (compressed.length == 0)) {
			throw new IllegalArgumentException("Cannot unzip null or empty bytes");
		}

		if (!isZipped(compressed)) {
			return new String(compressed);
		}

		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,
						StandardCharsets.UTF_8)) {
					try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
						StringBuilder output = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							output.append(line);
						}
						return output.toString();
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to unzip content", e);
		}
	}

	/**
	 * 檢查是否為 Gzip 格式
	 * @param compressed 壓縮字串
	 * @return boolean
	 */
	public static boolean isZipped(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
				&& (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}
}
