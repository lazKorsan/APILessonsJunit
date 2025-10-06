package tests;

import org.json.JSONObject;
import org.junit.Test;

public class P02_jsonDataOlusturma {

    @Test

    public void TC_01(){
    /*
    Asagidaki JSON Objesini olusturup konsolda yazdirin.

	{
	"title":"Ahmet",
	"body":"Merhaba",
	"userId":1
	}
 */

        JSONObject data=new JSONObject();
        data.put("title","Ahmet");
        data.put("body","Merhaba");
        data.put("userId",1);

        System.out.println(data);
    }

    @Test
    public void TC_02(){ //JSON data olusturma
         /*
        {
     "firstname":"Jim",
     "lastname":"Brown",
     "totalprice":111,
     "depositpaid":true,
     "bookingdates": {
             "checkin":"2018-01-01",
             "checkout":"2019-01-01"
                    },
     "additionalneeds":"Breakfast"
 }
  */
        //İç içe dataların bulunduğu verilerde biz en içtekini oluşturarak başlarız

        JSONObject bookingdates=new JSONObject();
        bookingdates.put("checkin","2018-01-01");
        bookingdates.put("checkout","2019-01-01");

        JSONObject data=new JSONObject();
        data.put("firstname","Jim");
        data.put("lastname","Brown");
        data.put("totalprice","111");
        data.put("depositpaid",true);
        data.put("bookingdates",bookingdates);
        data.put("additionalneeds","Breakfast");

        System.out.println(data);


    }




}
