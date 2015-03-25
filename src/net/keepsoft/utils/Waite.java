package net.keepsoft.utils;

import com.thoughtworks.selenium.Selenium;

@SuppressWarnings("deprecation")
public class Waite {
static boolean exists = false;
static boolean found;
 
public static int getNumberOfElements(Selenium selenium, String element) {
   return selenium.getXpathCount(element).intValue();
}
public static boolean tillTextPresent(Selenium selenium, String txt){
   //selenium.waitForPageToLoad("30000");
   found = false;
   int i=0;
   int x;
   int y;
   do{
      exists = selenium.isTextPresent(txt);
      if(exists) {
         found = true;
      }
      i++;
      //found = false;
      if(i == 3) {
         x = getNumberOfElements(selenium, "//iframe");
         for(int j=0; j< x-1; j++) {
            selenium.selectFrame("index="+(j+1));
            y = getNumberOfElements(selenium, "//iframe");
            for(int k=0; k< y; k++) {
               selenium.selectFrame("index="+k);
               exists = selenium.isTextPresent(txt);
 
               if(exists) {
                  k = y+1;
          j = x+1;
          found = true;
           }
           else {
              selenium.selectFrame("relative=up");
          i = 2;
           }
        }
        exists = selenium.isTextPresent(txt);
        if(exists) {
           j = x+1;
           i = 4;
           found = true;
        }
        else {
           selenium.selectFrame("relative=up");
        }
         }
         exists = true; //This is to get out of the loop
      }
      selenium.selectFrame("relative=top");
   }
   while(!exists);
   return found;
}
}