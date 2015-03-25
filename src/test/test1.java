package test;

import java.net.URLEncoder;

public class test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		JFILECHOOSER CHOOSER = NEW JFILECHOOSER("E:/");
//		CHOOSER.SETFILESELECTIONMODE(JFILECHOOSER.SAVE_DIALOG | JFILECHOOSER.DIRECTORIES_ONLY);
//		SYSTEM.OUT.PRINTLN(00000);
//		INT RESULT = CHOOSER.SHOWSAVEDIALOG(NULL);
//		IF(RESULT == JFILECHOOSER.APPROVE_OPTION){
//			STRING PATH = CHOOSER.GETSELECTEDFILE().GETABSOLUTEPATH();
//			SYSTEM.OUT.PRINTLN(PATH);
//		}
//		ELSE{
//			 SYSTEM.OUT.PRINTLN("你已取消并关闭了窗口！");
//		}
//		String str;
//		Calendar calTime;
//		int i;
//		System.out.println(null+"");
		
		
			String temp = URLEncoder.encode("你猜");
			System.err.println(temp);
		}

}
