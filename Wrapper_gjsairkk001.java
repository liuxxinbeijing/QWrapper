import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.StringUtils;

import com.qunar.qfwrapper.bean.booking.BookingInfo;
import com.qunar.qfwrapper.bean.booking.BookingResult;
import com.qunar.qfwrapper.bean.search.FlightDetail;
import com.qunar.qfwrapper.bean.search.FlightSearchParam;
import com.qunar.qfwrapper.bean.search.FlightSegement;
import com.qunar.qfwrapper.bean.search.OneWayFlightInfo;
import com.qunar.qfwrapper.bean.search.ProcessResultInfo;
import com.qunar.qfwrapper.bean.search.RoundTripFlightInfo;
import com.qunar.qfwrapper.constants.Constants;
import com.qunar.qfwrapper.interfaces.QunarCrawler;
import com.qunar.qfwrapper.util.QFHttpClient;
import com.qunar.qfwrapper.util.QFPostMethod;

public class Wrapper_gjsairkk001 implements QunarCrawler{

	/**
	 * @param args
	 */
public String getHtml(FlightSearchParam arg0) {
		
		QFPostMethod post = null;
		try
		{
		// get all query parameters from the url set by wrapperSearchInterface
		QFHttpClient httpClient = new QFHttpClient(arg0, false);
		httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		// 闂備緡鍋呮穱铏规崲閸愨晝顩烽柨婵嗘处閸婄偤鎮规担鍛婂仴婵?
		//httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
		//Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(), 443));		
				
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date depDate = format.parse(arg0.getDepDate());
		Date retDate = format.parse(arg0.getRetDate());
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");  
		String strDateDepDate = sdf.format(depDate);
		String strDateRetDate = sdf.format(retDate);

		
		//String depdate=arg0.getDepDate().replaceAll("-","")+"0000";	
		//String retdate=arg0.getRetDate().replaceAll("-","")+"0000";	
		post = new QFPostMethod("https://online.atlasjet.com/AtlasOnline/availability.kk");		
		NameValuePair[] names = {
				//new NameValuePair("FromCode","ADA"),
				new NameValuePair("from",arg0.getDep()),
				new NameValuePair("to",arg0.getArr()),
				new NameValuePair("lang","EN"),
				new NameValuePair("direction","0"),
				new NameValuePair("depdate",strDateDepDate),
				new NameValuePair("retdate",strDateRetDate),
				new NameValuePair("adult","1"),
				new NameValuePair("yp","0"),
				new NameValuePair("chd","0"),
				new NameValuePair("inf","0"),
				new NameValuePair("sc","0"),
				new NameValuePair("stu","0"),
				new NameValuePair("tsk","0"),
				new NameValuePair("refid","www.atlasjet.com"),
				new NameValuePair("paramstatus","1"),
				new NameValuePair("openjaw",""),
				new NameValuePair("bannerSize","200x200"),
				new NameValuePair("curr","USD")
		};
		post.setRequestBody(names);
		//String cookie = StringUtils.join(httpClient.getState().getCookies(),"; ");	
		//httpClient.getState().clearCookies();
		//post.addRequestHeader("Cookie","superT_v1=1404095351754.181863%3A1%3A1%3A1; superT_s1=1404095351757.26231; __utma=166957934.233118461.1404095355.1404095355.1404095355.1; __utmb=166957934.12.8.1404095421275; __utmc=166957934; __utmz=166957934.1404095355.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
		post.setRequestHeader("Referer", "http://www.atlasjet.com/MainPage");
		//post.getParams().setContentCharset("UTF-8");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		httpClient.executeMethod(post);	
		
		
		//System.out.println(post.getResponseBodyAsString().toString());
		return post.getResponseBodyAsString();
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {			
			if (post != null) {
				post.releaseConnection();
			}
		}
		return "Exception";
		    
	}

	@Override
	public BookingResult getBookingInfo(FlightSearchParam arg0) {
		String bookingUrlPre = "https://online.atlasjet.com/AtlasOnline/availability.kk";
		BookingResult bookingResult = new BookingResult();
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setAction(bookingUrlPre);
		bookingInfo.setMethod("post");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date depDate = null;
		Date retDate = null;
		try {
			depDate = format.parse(arg0.getDepDate());
			retDate = format.parse(arg0.getRetDate());
		}	catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");  
		String strDateDepDate = sdf.format(depDate);
		String strDateRetDate = sdf.format(retDate);
		
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("from",arg0.getDep());
		map.put("to",arg0.getArr());
		map.put("lang","EN");
		map.put("direction","0");
		map.put("depdate",strDateDepDate);
		map.put("retdate",strDateRetDate);
		map.put("adult","1");
		map.put("yp","0");
		map.put("chd","0");
		map.put("inf","0");
		map.put("sc","0");
		map.put("stu","0");
		map.put("tsk","0");
		map.put("refid","www.atlasjet.com");
		map.put("paramstatus","1");
		map.put("openjaw","");
		map.put("bannerSize","200x200");
		//map.put("curr","USD");		
		
		bookingInfo.setContentType("UTF-8");
		bookingInfo.setInputs(map);
		bookingResult.setData(bookingInfo);
		bookingResult.setRet(true);
		return bookingResult;
	}



	public ProcessResultInfo process(String arg0, FlightSearchParam arg1) {
		String html = arg0;
		ProcessResultInfo result = new ProcessResultInfo();
		if ("Exception".equals(html)) {
			result.setRet(false);
			result.setStatus(Constants.CONNECTION_FAIL);
			return result;	
		}
		if (html.contains("\"Result\":false")){
			result.setRet(true);
			result.setStatus(Constants.NO_RESULT);
			return result;	
		}
		
		try {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date retDate = format.parse(arg1.getRetDate());
		Date depDate = format.parse(arg1.getDepDate());
		// 闁告瑦鐗曢崵顓㈠储閼姐倐鏌ら柤鍓蹭簽瑜邦喗绌遍埄鍐х礀
		String html2 = StringUtils.substringBetween(html,"<tbody>" ,"</tbody>");
		String[] flights = html2.split("<tr class=\"showContent\">");
		List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			String Price = "";
			String Pricer="";
			String moneyUnit =  "";
			for (int i = 1; i < flights.length; i++){
				// 婵☆偅婢樼€氼剟宕㈠☉娆忕窞闁告洦鍘介崐婵皌ml
				String flightHtml = flights[i];
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 闁哄鏅涘ú锕傚箮閵堝妫橀柛銉㈡櫇瑜板潡鏌涢幇顒佸櫣妞ゆ梹鍔栫粙澶愵敇閻樺磭鏆爃tml
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 闂佸憡锚椤嘲鈻嶆惔锝囩煔闁绘垶顭囨竟鎰版煏閸℃洜顦﹂柛鈺傤殘閹即濡歌閸庡﹪鏌曢崱鏇狀槮婵炲弶鐗楀顏堟晜閽樺绨ラ柣?
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 闂佸憡锚椤嘲鈻嶆惔锝囩煔闁绘垶顭囨竟?
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 閻庢鍠掗崑鎾斥攽椤旂⒈鍎撶悮娆撴煛?..
				String DepartureFlight = StringUtils.substringBetween(flights[i], "<td  style=\"\" title=\"\">", "</td>");
				DepartureFlight = DepartureFlight.replaceAll("\\s*|\t|\r|\n", "");
				int zzbz = StringUtils.indexOf(flights[i], "rowspan=\"");	// 婵炴垶鎼╅崣鈧繛鏉戞瀵粙宕惰缁?
				if (zzbz >= 0) {
					String temp = StringUtils.substringBetween(flights[i], "rowspan=\"", "\"");
					zzbz = Integer.parseInt(temp);
				}
				if (zzbz > 0) {	// 闂佸搫鍊规刊浠嬪春閸涘瓨鍋?
					String flightHtmlPrice = StringUtils.substringBetween(flights[i], "<label", "label"); // 闂佽鎯屾禍婊嗐亹閸ャ劎顩烽梺鍨儑婢?
					flightHtmlPrice = StringUtils.substringBetween(flightHtmlPrice, ">","<");
					Pattern pt=Pattern.compile("([0-9]|\\.|\\-)*");
					Matcher m=pt.matcher(flightHtmlPrice);
					m.find();
					Price=m.group();
					moneyUnit =  flightHtmlPrice.substring(Price.length());
					moneyUnit = moneyUnit.replaceAll("\\s*|\t|\r|\n", "");
					if(moneyUnit.equals("$")){
						moneyUnit="USD";
					}
				} else {	// 婵炴垶鎼╅崣鈧繛鏉戞閹虫粓顢旈崱妤冩瀭
					// 闂備焦褰冨ú銊╁极閵堝棛鈻斿┑鐘辫兌椤忛亶鏌ｅ┑鎰箳闁绘粠鍨跺?
				}
				
				// 闁荤姳绀佹晶浠嬫偪閸濈seFlight
				if (zzbz > 0) {
					OneWayFlightInfo baseFlight = new OneWayFlightInfo();
					FlightDetail flightDetail = new FlightDetail();
					List<FlightSegement> segs = new ArrayList<FlightSegement>();
					
					//	闁荤姳绀佹晶浠嬫偪閸炰康ightDetail
					List<String> flightNoList = new ArrayList<String>();
					flightNoList.add(DepartureFlight);
					flightDetail.setFlightno(flightNoList);
					flightDetail.setDepdate(depDate);
					flightDetail.setMonetaryunit(moneyUnit);
					
					flightDetail.setPrice(Double.parseDouble(Price));
					flightDetail.setDepcity(arg1.getDep());
					flightDetail.setArrcity(arg1.getArr());
					flightDetail.setWrapperid(arg1.getWrapperid());
					baseFlight.setDetail(flightDetail);
					
					// 闁荤姳绀佹晶浠嬫偪閸滅ightSegement
					String deptimes = flightHtml.substring(0, 5);
					String arrtimes = flightHtml.substring(5, 10);
					FlightSegement seg = new FlightSegement();
					seg.setFlightno(DepartureFlight);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
					String strDate = sdf.format(depDate);  
					seg.setDepDate(strDate);
					seg.setArrDate(strDate);
					seg.setDepairport(arg1.getDep());
					seg.setArrairport(arg1.getArr());
					seg.setDeptime(deptimes);
					seg.setArrtime(arrtimes);
					segs.add(seg);
					
					baseFlight.setInfo(segs);
					flightList.add(baseFlight);
				} else {	// 婵炴垶鎼╅崣鈧繛鏉戞閹虫粓顢旈崱妤冩瀭
					OneWayFlightInfo baseFlight = flightList.get(flightList.size() - 1);
					FlightDetail flightDetail = baseFlight.getDetail();
					List<FlightSegement> segs = baseFlight.getInfo();
					
					//	闁荤姳绀佹晶浠嬫偪閸炰康ightDetail
					List<String> flightNoList = flightDetail.getFlightno();
					flightNoList.add(DepartureFlight);
					flightDetail.setFlightno(flightNoList);
					baseFlight.setDetail(flightDetail);

					String deptimes = flightHtml.substring(0, 5);
					String arrtimes = flightHtml.substring(5, 10);
					FlightSegement seg = new FlightSegement();
					seg.setFlightno(DepartureFlight);
					//SimpleDateFormat formatr = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
					String strDate = sdf.format(depDate);  
					seg.setDepDate(strDate);
					seg.setArrDate(strDate);
					seg.setDepairport(arg1.getDep());
					seg.setArrairport(arg1.getArr());
					seg.setDeptime(deptimes);
					seg.setArrtime(arrtimes);
					segs.add(seg);
					baseFlight.setInfo(segs);
				}
			}
				// 闁告瑦鐗曢崵顓熸交閺冨倵鏌ら柤鍓蹭簽瑜邦喗绌遍埄鍐х礀
				String halfHtml = StringUtils.substringAfter(html, "<div id=\"plan1\">");
				String htmlr = StringUtils.substringBetween(halfHtml,"<tbody>" ,"</tbody>");
				String[] flightsr = htmlr.split("<tr class=\"showContent\">");					
					List<RoundTripFlightInfo> flightListr = new ArrayList<RoundTripFlightInfo>();
 					for (int j = 1; j < flightsr.length; j++){
						// 婵☆偅婢樼€氼剟宕㈠☉娆忕窞闁告洦鍘介崐婵皌ml
						String flightHtmlr = flightsr[j];
						System.out.println(flightHtmlr);
						flightHtmlr = flightHtmlr.replaceAll("</?[^<]+>", "");	// 闁哄鏅涘ú锕傚箮閵堝妫橀柛銉㈡櫇瑜板潡鏌涢幇顒佸櫣妞ゆ梹鍔栫粙澶愵敇閻樺磭鏆爃tml
						flightHtmlr = flightHtmlr.replaceAll("\\s*|\t|\r|\n", "");	// 闂佸憡锚椤嘲鈻嶆惔锝囩煔闁绘垶顭囨竟鎰版煏閸℃洜顦﹂柛鈺傤殘閹即濡歌閸庡﹪鏌曢崱鏇狀槮婵炲弶鐗楀顏堟晜閽樺绨ラ柣?
						flightHtmlr = flightHtmlr.replaceAll("&nbsp;:", "");	// 闂佸憡锚椤嘲鈻嶆惔锝囩煔闁绘垶顭囨竟?
						flightHtmlr = flightHtmlr.replaceAll("&nbsp;", "");						
						// 閻庢鍠掗崑鎾斥攽椤旂⒈鍎撶悮娆撴煛?..
						String DepartureFlightr = StringUtils.substringBetween(flightsr[j], "<td  style=\"\" title=\"\">", "</td>");
						DepartureFlightr = DepartureFlightr.replaceAll("\\s*|\t|\r|\n", "");
						int zzb = StringUtils.indexOf(flightsr[j], "rowspan=\"");	// 婵炴垶鎼╅崣鈧繛鏉戞瀵粙宕惰缁?
						if (zzb>= 0) {
							String tempr = StringUtils.substringBetween(flightsr[j], "rowspan=\"", "\"");
							zzb = Integer.parseInt(tempr);
						}
						if (zzb> 0) {	// 闂佸搫鍊规刊浠嬪春閸涘瓨鍋?
							String flightHtmlPricer= StringUtils.substringBetween(flightsr[j], "<label", "label"); // 闂佽鎯屾禍婊嗐亹閸ャ劎顩烽梺鍨儑婢?
							flightHtmlPricer = StringUtils.substringBetween(flightHtmlPricer, ">","<");
							Pattern ptr=Pattern.compile("([0-9]|\\.|\\-)*");
							Matcher mr=ptr.matcher(flightHtmlPricer);
							mr.find();
							Pricer=mr.group();
						} else {	// 婵炴垶鎼╅崣鈧繛鏉戞閹虫粓顢旈崱妤冩瀭
							// 闂備焦褰冨ú銊╁极閵堝棛鈻斿┑鐘辫兌椤忛亶鏌ｅ┑鎰箳闁绘粠鍨跺?
						}
						
						// 闁荤姳绀佹晶浠嬫偪閸濈seFlight
						if (zzb> 0) {
							RoundTripFlightInfo baseFlightr = new RoundTripFlightInfo();
							//FlightDetail flightDetailr = new FlightDetail();
							List<FlightSegement> segsr = new ArrayList<FlightSegement>();
							
							//	闁荤姳绀佹晶浠嬫偪閸炰康ightDetail
							List<String> flightNoListr = new ArrayList<String>();
							flightNoListr.add(DepartureFlightr);
							baseFlightr.setRetflightno(flightNoListr);
							//flightDetailr.setFlightno(flightNoListr);
							//flightDetailr.setDepdate(retDate);
							baseFlightr.setRetdepdate(retDate);
							baseFlightr.setReturnedPrice(Double.parseDouble(Pricer));
							//flightDetailr.setPrice(Double.parseDouble(Pricer));
							//flightDetailr.setDepcity(arg1.getArr());
							//flightDetailr.setArrcity(arg1.getDep());
							//flightDetailr.setWrapperid(arg1.getWrapperid());
							//baseFlightr.setDetail(flightDetailr);
							
							// 闁荤姳绀佹晶浠嬫偪閸滅ightSegement
							String deptimesr = flightHtmlr.substring(0, 5);
							String arrtimesr = flightHtmlr.substring(5, 10);
							FlightSegement segr = new FlightSegement();
							segr.setFlightno(DepartureFlightr);
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
							String strDate = sdf.format(retDate);  
							segr.setDepDate(strDate);//?閺夆晜鐟ュ鈩冪▔瀹ュ洨鍙€闂侇剚鎸搁顔界▔?							
							segr.setArrDate(strDate);
							segr.setDepairport(arg1.getArr());
							segr.setArrairport(arg1.getDep());
							segr.setDeptime(deptimesr);
							segr.setArrtime(arrtimesr);
							segsr.add(segr);
							
							baseFlightr.setRetinfo(segsr);
							flightListr.add(baseFlightr);
						}else {	// 婵炴垶鎼╅崣鈧繛鏉戞閹虫粓顢旈崱妤冩瀭
							RoundTripFlightInfo baseFlightr = flightListr.get(flightListr.size() - 1);
							//FlightDetail flightDetailr = baseFlightr.getDetail();
							List<FlightSegement> segsr = baseFlightr.getRetinfo();
							
							//	闁荤姳绀佹晶浠嬫偪閸炰康ightDetail
							List<String> flightNoListr = baseFlightr.getRetflightno();
							flightNoListr.add(DepartureFlightr);
							baseFlightr.setRetflightno(flightNoListr);
							//baseFlightr.setDetail(flightDetailr);

							String deptimesr = flightHtmlr.substring(0, 5);
							String arrtimesr = flightHtmlr.substring(5, 10);
							FlightSegement segr = new FlightSegement();
							segr.setFlightno(DepartureFlightr);
							SimpleDateFormat formatr = new SimpleDateFormat("yyyy-MM-dd");
							Date date = format.parse(arg1.getDepDate());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
							String strDate = sdf.format(date);  
							segr.setDepDate(strDate);
							segr.setArrDate(strDate);
							segr.setDepairport(arg1.getArr());
							segr.setArrairport(arg1.getDep());
							segr.setDeptime(deptimesr);
							segr.setArrtime(arrtimesr);
							segsr.add(segr);
							baseFlightr.setRetinfo(segsr);
						}
 					}
			//缂備礁瀚幃搴ㄥ箚閸涱厼鏋岄柨?						
 					List<RoundTripFlightInfo> allList = new ArrayList<RoundTripFlightInfo>();
						for (int qc = 0;qc < flightList.size();qc++) {
							for (int fc = 0;fc < flightListr.size();fc++) {
								OneWayFlightInfo qcFlightInfo = flightList.get(qc);
								RoundTripFlightInfo fcFlightInfo = flightListr.get(fc);
								
								// 缂備礁瀚幃?
								RoundTripFlightInfo roundTripFlightInfo = new RoundTripFlightInfo();
								roundTripFlightInfo.setRetdepdate(fcFlightInfo.getRetdepdate());
								roundTripFlightInfo.setRetflightno(fcFlightInfo.getRetflightno());
								roundTripFlightInfo.setRetinfo(fcFlightInfo.getRetinfo());
								roundTripFlightInfo.setOutboundPrice(qcFlightInfo.getDetail().getPrice());
								roundTripFlightInfo.setReturnedPrice(fcFlightInfo.getReturnedPrice());
								roundTripFlightInfo.setInfo(qcFlightInfo.getInfo());
								FlightDetail qcFlightDetail = new FlightDetail();
								FlightDetail oldFlightDetail=qcFlightInfo.getDetail();
								qcFlightDetail.setArrcity(oldFlightDetail.getArrcity());
								qcFlightDetail.setDepcity(oldFlightDetail.getDepcity());
								qcFlightDetail.setDepdate(oldFlightDetail.getDepdate());
								qcFlightDetail.setFlightno(oldFlightDetail.getFlightno());
								qcFlightDetail.setMonetaryunit(oldFlightDetail.getMonetaryunit());
								qcFlightDetail.setPrice(oldFlightDetail.getPrice() + fcFlightInfo.getReturnedPrice());	
								qcFlightDetail.setWrapperid(oldFlightDetail.getWrapperid());
								//闁告艾鐗嗛懟鐔煎箑鐠佸磭骞?								
								roundTripFlightInfo.setDetail(qcFlightDetail);								
								allList.add(roundTripFlightInfo);							
							}
						
						}
		result.setRet(true);
		result.setStatus(Constants.SUCCESS);
		result.setData(allList);
		return result;
						}
		catch(Exception e){
		e.printStackTrace();
		result.setRet(false);
		//result.setStatus(Constants.PARSING_FAIL);
		result.setStatus("<textarea>" + html + "<textarea>" + "<textarea>" + e.toString() + "<textarea>");
		
		return result;
	}
	}
	
}
