package com.aamu.aamurest.user.web;

import java.io.File;

public class FileUploadUtils {
	// [���� �̸� �ߺ� üũ�� �޼ҵ�]
	public static String getNewFileName(String path, String fileName) {
		// fileName=����.txt
		File file = new File(path + File.separator + fileName);
		if (!file.exists()) {
			return fileName;
		} else {
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).trim();
			String fileNameExcludeExt = fileName.substring(0, fileName.lastIndexOf("."));

			String newFileName;
			while (true) {
				newFileName = "";
				if (fileNameExcludeExt.indexOf("_") != -1) {// ���ϸ� _�� ���Ե�
					String[] arrFiles = fileNameExcludeExt.split("_");
					String lastName = arrFiles[arrFiles.length - 1];
					try {
						int index = Integer.parseInt(lastName);
						for (int i = 0; i < arrFiles.length; i++) {
							if (i != arrFiles.length - 1)
								newFileName += arrFiles[i] + "_";
							else
								newFileName += String.valueOf(index + 1);
						}
						newFileName += "." + ext;
					} catch (Exception e) {
						newFileName += fileNameExcludeExt + "_1." + ext;
					}
				} else {// _�� ����
					newFileName += fileNameExcludeExt + "_1." + ext;
				}
				File f = new File(path + File.separator + newFileName);
				if (f.exists()) {
					fileNameExcludeExt = newFileName.substring(0, newFileName.lastIndexOf("."));
					continue;
				} else
					break;
			} //////////// while

			return newFileName;
		}
	}
}
