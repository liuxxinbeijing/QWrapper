
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
				new NameValuePair("bannerSize","200x200")
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
		String bookingUrlPre = "https://online.atlasjet.com/AtlasOnline/passenger.kk";
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
		String strRetRetDate = sdf.format(retDate);
		
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("historyCookieId			","																						");
		map.put("selectedPlan0Class","20140724ATLJETADAISTKK31#YD#09:05#67.0");
		map.put("selectedPlan1Class","20140731ATLJETISTADAKK30#YX#07:00#77.0");
		map.put("selectedPlan2Class","");
		map.put("selectedPlan3Class","");
		map.put("selectedPlan4Class","");
		map.put("selectedPlan5Class","");
		map.put("flightPlan0","20140724ATLJETADAISTKK31#YD#09:05#67.00");
		map.put("flightPlan1","20140731ATLJETISTADAKK30#YX#07:00#77.00");
		map.put("isAwardJetmilPage","false");
		map.put("direction","0");
		map.put("from",arg0.getDep());
		map.put("to",arg0.getArr());
		map.put("depdate",strDateDepDate);
		map.put("retdate",strRetRetDate);
		map.put("adult","1");
		map.put("yp","0");
		map.put("chd","0");
		map.put("inf","0");
		map.put("stu","0");
		map.put("tsk","0");
		map.put("sc","0");
		map.put("wherefrom","searchpage");
		map.put("selectedTo",arg0.getArr());
		map.put("selectedOpenJaw","");
		map.put("currentDeptDate",strDateDepDate);
		map.put("currentRetDate",strRetRetDate);
		map.put("fromDesc",arg0.getDep());//闁告绮敮鈧ù? "NA"
		map.put("toDesc",arg0.getArr());
		map.put("openjawDesc","");
		map.put("curr","null");
		map.put("totalBasePrice","131 TL");
		map.put("totalBasePriceAsTL","131");
		map.put("totalTaxPrice","63 TL");
		map.put("totalServiceFeePrice","10 TL");
		map.put("totalPrice","204 TL");
		map.put("totalPriceAsTL"," (204 TL)");
		map.put("totalPassengerCount","1");
		map.put("totalJetmilPoint","");
		
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
		// 闂佸憡鐟﹂悧鏇㈠吹椤撱垹鍌ㄩ柤濮愬€愰弻銈夋煠閸撹弓绨界憸閭﹀枟缁岄亶鍩勯崘褏绀€
		String html2 = StringUtils.substringBetween(html,"<tbody>" ,"</tbody>");
		String[] flights = html2.split("<tr class=\"showContent\">");
		List<OneWayFlightInfo> flightList = new ArrayList<OneWayFlightInfo>();
			String Price = "";
			String Pricer="";
			String moneyUnit =  "";
			for (int i = 1; i < flights.length; i++){
				// 濠碘槅鍋呭妯尖偓姘煎墴瀹曘垹鈽夊▎蹇曠獮闂佸憡娲﹂崢浠嬪磹濠电殞ml
				String flightHtml = flights[i];
				flightHtml = flightHtml.replaceAll("</?[^<]+>", "");	// 闂佸搫顦弲娑樏洪敃鍌氱闁靛牆顦Λ姗€鏌涢妷銏℃珖鐟滄澘娼￠弻娑㈠箛椤掍礁娅ｅ銈嗘⒐閸旀牜绮欐径鎰垫晣闁绘ê纾弳鐖僼ml
				flightHtml = flightHtml.replaceAll("\\s*|\t|\r|\n", "");	// 闂備礁鎲￠敋妞ゎ厼鍢查埢宥嗘償閿濆洨鐓旈梺缁樺灦椤洦绔熼幇鐗堢厪闁糕剝娲滈ˇ锕傛煕閳哄偆娈橀柟顔诲嵆婵℃瓕顧侀柛搴★躬閺屾洟宕遍弴鐙€妲┑鐐插级閻楁顭囬鍫熸櫆闁芥ê顦花銉╂煟?
				flightHtml = flightHtml.replaceAll("&nbsp;:", "");	// 闂備礁鎲￠敋妞ゎ厼鍢查埢宥嗘償閿濆洨鐓旈梺缁樺灦椤洦绔?
				flightHtml = flightHtml.replaceAll("&nbsp;", "");
				
				// 闁诲孩顔栭崰鎺楀磻閹炬枼鏀芥い鏃傗拡閸庢挾鎮▎鎾寸厸?..
				String DepartureFlight = StringUtils.substringBetween(flights[i], "<td  style=\"\" title=\"\">", "</td>");
				DepartureFlight = DepartureFlight.replaceAll("\\s*|\t|\r|\n", "");
				int zzbz = StringUtils.indexOf(flights[i], "rowspan=\"");	// 濠电偞鍨堕幖鈺呭矗閳ь剚绻涢弶鎴烆棦鐎殿喕绮欏畷鎯邦槾缂?
				if (zzbz >= 0) {
					String temp = StringUtils.substringBetween(flights[i], "rowspan=\"", "\"");
					zzbz = Integer.parseInt(temp);
				}
				if (zzbz > 0) {	// 闂備礁鎼崐瑙勫垔娴犲鏄ラ柛娑樼摠閸?
					String flightHtmlPrice = StringUtils.substringBetween(flights[i], "<label", "label"); // 闂備浇顫夐幆灞剧濠婂棎浜归柛銉ｅ妿椤╃兘姊洪崹顕呭剳濠?
					flightHtmlPrice = StringUtils.substringBetween(flightHtmlPrice, ">","<");
					Pattern pt=Pattern.compile("([0-9]|\\.|\\-)*");
					Matcher m=pt.matcher(flightHtmlPrice);
					m.find();
					Price=m.group();
					moneyUnit =  flightHtmlPrice.substring(Price.length());
					moneyUnit = moneyUnit.replaceAll("\\s*|\t|\r|\n", "");
					if(moneyUnit.equals("TL")){
						moneyUnit="TRL";
					}
				} else {	// 濠电偞鍨堕幖鈺呭矗閳ь剚绻涢弶鎴烆棦闁硅櫕绮撻、鏃堝幢濡ゅ啯鐎?					// 闂傚倷鐒﹁ぐ鍐洪妸鈺佹瀬闁靛牆妫涢埢鏂库攽閻樿精鍏屾い蹇涗憾閺岋絽鈹戦幇顒€绠抽梺缁樼矤閸ㄨ泛顕?
				}
				
				// 闂佽崵濮崇粈浣规櫠娴犲鍋柛婵堫劆seFlight
				if (zzbz > 0) {
					OneWayFlightInfo baseFlight = new OneWayFlightInfo();
					FlightDetail flightDetail = new FlightDetail();
					List<FlightSegement> segs = new ArrayList<FlightSegement>();
					
					//	闂佽崵濮崇粈浣规櫠娴犲鍋柛鐐板悍ightDetail
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
					
					// 闂佽崵濮崇粈浣规櫠娴犲鍋柛婊咁潛ightSegement
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
				} else {	// 濠电偞鍨堕幖鈺呭矗閳ь剚绻涢弶鎴烆棦闁硅櫕绮撻、鏃堝幢濡ゅ啯鐎?					OneWayFlightInfo baseFlight = flightList.get(flightList.size() - 1);
					FlightDetail flightDetail = baseFlight.getDetail();
					List<FlightSegement> segs = baseFlight.getInfo();
					
					//	闂佽崵濮崇粈浣规櫠娴犲鍋柛鐐板悍ightDetail
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
				// 闂佸憡鐟﹂悧鏇㈠吹椤撶喐浜ら柡鍐ㄥ€甸弻銈夋煠閸撹弓绨界憸閭﹀枟缁岄亶鍩勯崘褏绀€
				String halfHtml = StringUtils.substringAfter(html, "<div id=\"plan1\">");
				String htmlr = StringUtils.substringBetween(halfHtml,"<tbody>" ,"</tbody>");
				String[] flightsr = htmlr.split("<tr class=\"showContent\">");					
					List<RoundTripFlightInfo> flightListr = new ArrayList<RoundTripFlightInfo>();
 					for (int j = 1; j < flightsr.length; j++){
						// 濠碘槅鍋呭妯尖偓姘煎墴瀹曘垹鈽夊▎蹇曠獮闂佸憡娲﹂崢浠嬪磹濠电殞ml
						String flightHtmlr = flightsr[j];
						System.out.println(flightHtmlr);
						flightHtmlr = flightHtmlr.replaceAll("</?[^<]+>", "");	// 闂佸搫顦弲娑樏洪敃鍌氱闁靛牆顦Λ姗€鏌涢妷銏℃珖鐟滄澘娼￠弻娑㈠箛椤掍礁娅ｅ銈嗘⒐閸旀牜绮欐径鎰垫晣闁绘ê纾弳鐖僼ml
						flightHtmlr = flightHtmlr.replaceAll("\\s*|\t|\r|\n", "");	// 闂備礁鎲￠敋妞ゎ厼鍢查埢宥嗘償閿濆洨鐓旈梺缁樺灦椤洦绔熼幇鐗堢厪闁糕剝娲滈ˇ锕傛煕閳哄偆娈橀柟顔诲嵆婵℃瓕顧侀柛搴★躬閺屾洟宕遍弴鐙€妲┑鐐插级閻楁顭囬鍫熸櫆闁芥ê顦花銉╂煟?
						flightHtmlr = flightHtmlr.replaceAll("&nbsp;:", "");	// 闂備礁鎲￠敋妞ゎ厼鍢查埢宥嗘償閿濆洨鐓旈梺缁樺灦椤洦绔?
						flightHtmlr = flightHtmlr.replaceAll("&nbsp;", "");						
						// 闁诲孩顔栭崰鎺楀磻閹炬枼鏀芥い鏃傗拡閸庢挾鎮▎鎾寸厸?..
						String DepartureFlightr = StringUtils.substringBetween(flightsr[j], "<td  style=\"\" title=\"\">", "</td>");
						DepartureFlightr = DepartureFlightr.replaceAll("\\s*|\t|\r|\n", "");
						int zzb = StringUtils.indexOf(flightsr[j], "rowspan=\"");	// 濠电偞鍨堕幖鈺呭矗閳ь剚绻涢弶鎴烆棦鐎殿喕绮欏畷鎯邦槾缂?
						if (zzb>= 0) {
							String tempr = StringUtils.substringBetween(flightsr[j], "rowspan=\"", "\"");
							zzb = Integer.parseInt(tempr);
						}
						if (zzb> 0) {	// 闂備礁鎼崐瑙勫垔娴犲鏄ラ柛娑樼摠閸?
							String flightHtmlPricer= StringUtils.substringBetween(flightsr[j], "<label", "label"); // 闂備浇顫夐幆灞剧濠婂棎浜归柛銉ｅ妿椤╃兘姊洪崹顕呭剳濠?
							flightHtmlPricer = StringUtils.substringBetween(flightHtmlPricer, ">","<");
							Pattern ptr=Pattern.compile("([0-9]|\\.|\\-)*");
							Matcher mr=ptr.matcher(flightHtmlPricer);
							mr.find();
							Pricer=mr.group();
						} else {	// 濠电偞鍨堕幖鈺呭矗閳ь剚绻涢弶鎴烆棦闁硅櫕绮撻、鏃堝幢濡ゅ啯鐎?							// 闂傚倷鐒﹁ぐ鍐洪妸鈺佹瀬闁靛牆妫涢埢鏂库攽閻樿精鍏屾い蹇涗憾閺岋絽鈹戦幇顒€绠抽梺缁樼矤閸ㄨ泛顕?
						}
						
						// 闂佽崵濮崇粈浣规櫠娴犲鍋柛婵堫劆seFlight
						if (zzb> 0) {
							RoundTripFlightInfo baseFlightr = new RoundTripFlightInfo();
							//FlightDetail flightDetailr = new FlightDetail();
							List<FlightSegement> segsr = new ArrayList<FlightSegement>();
							
							//	闂佽崵濮崇粈浣规櫠娴犲鍋柛鐐板悍ightDetail
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
							
							// 闂佽崵濮崇粈浣规櫠娴犲鍋柛婊咁潛ightSegement
							String deptimesr = flightHtmlr.substring(0, 5);
							String arrtimesr = flightHtmlr.substring(5, 10);
							FlightSegement segr = new FlightSegement();
							segr.setFlightno(DepartureFlightr);
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
							String strDate = sdf.format(retDate);  
							segr.setDepDate(strDate);//?闁哄鏅滈悷銉ヮ焽閳╁啰鈻旂€广儱娲ㄩ崣鈧梻渚囧墯閹告悂顢氶鐣屸枖?							
							segr.setArrDate(strDate);
							segr.setDepairport(arg1.getArr());
							segr.setArrairport(arg1.getDep());
							segr.setDeptime(deptimesr);
							segr.setArrtime(arrtimesr);
							segsr.add(segr);
							
							baseFlightr.setRetinfo(segsr);
							flightListr.add(baseFlightr);
						}else {	// 濠电偞鍨堕幖鈺呭矗閳ь剚绻涢弶鎴烆棦闁硅櫕绮撻、鏃堝幢濡ゅ啯鐎?							RoundTripFlightInfo baseFlightr = flightListr.get(flightListr.size() - 1);
							//FlightDetail flightDetailr = baseFlightr.getDetail();
							List<FlightSegement> segsr = baseFlightr.getRetinfo();
							
							//	闂佽崵濮崇粈浣规櫠娴犲鍋柛鐐板悍ightDetail
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
			//缂傚倷绀佺€氼剟骞冩惔銊ョ畾闁告侗鍘奸弸宀勬煥?						
 					List<RoundTripFlightInfo> allList = new ArrayList<RoundTripFlightInfo>();
						for (int qc = 0;qc < flightList.size();qc++) {
							for (int fc = 0;fc < flightListr.size();fc++) {
								OneWayFlightInfo qcFlightInfo = flightList.get(qc);
								RoundTripFlightInfo fcFlightInfo = flightListr.get(fc);
								
								// 缂傚倷绀佺€氼剟骞?
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
								//闂佸憡鑹鹃悧鍡涙嚐閻旂厧绠戦悹浣哥－楠?								
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
