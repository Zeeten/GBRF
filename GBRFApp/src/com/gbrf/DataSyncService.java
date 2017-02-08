package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gbrf.dto.UserDTO;

public class DataSyncService {

	String server_url = "theacademicworld.com";

	public UserDTO invitation(String uId, String nId, double latitude,
			double longitude) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String INVITATION_URL = "http://" + server_url
				+ "/api/rest/invitations/accept";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("nid", nId));
		param.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		param.add(new BasicNameValuePair("longitude", Double
				.toString(longitude)));
		// param.add(new BasicNameValuePair("ip_address", "192.168.1.200"));
		param.add(new BasicNameValuePair("device", "android"));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(INVITATION_URL, "POST",
				param);

		try {
			System.out.println("json" + json);
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String iid = jsonOBject.getString("iid");
					dto.setIid(iid);
					String releaseon = jsonOBject.getString("released_on");
					dto.setReleaseOn(releaseon);
					String accepted_on = jsonOBject.getString("accepted_on");
					dto.setAcceptedOn(accepted_on);
				}
			}
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				dto.setMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO invitationcheck(String uId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String INVITATION_URL = "http://" + server_url
				+ "/api/rest/invitations/check";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(INVITATION_URL, "GET",
				param);

		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String iid = jsonOBject.getString("iid");
					dto.setIid(iid);
					String releaseon = jsonOBject.getString("released_on");
					dto.setReleaseOn(releaseon);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO registration(String Name, String mobileNo, String emailId,
			String password, double latitude, double longitude) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String REGISTRATION_URL = "http://" + server_url
				+ "/api/rest/user/create";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("name", Name));
		param.add(new BasicNameValuePair("email", emailId));
		param.add(new BasicNameValuePair("phone", mobileNo));
		param.add(new BasicNameValuePair("password", password));
		param.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		param.add(new BasicNameValuePair("longitude", Double.toString(longitude)));
		param.add(new BasicNameValuePair("device", "android"));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(REGISTRATION_URL, "POST",
				param);
		System.out.println("json" + json);
		try {
			
			  data = json.getString("status");
			  dto.setStatus(json.getString("status"));
			  
			  if (json.getString("status").equalsIgnoreCase("success")) {
			  JSONArray userJson = json.getJSONArray("data");
			  for (int i = 0; i< userJson.length(); i++) 
			  { 
				  JSONObject jsonOBject = userJson.getJSONObject(i);
				  System.out.println(jsonOBject);
			  dto.setuId(jsonOBject.getString("uid"));
			  dto.setOtp(jsonOBject.getString("otp"));
			  dto.setPhone(jsonOBject.getString("phone"));
			  dto.setActivationCode(jsonOBject.getString("activation_code"));
			  System.out.println("otppp"+jsonOBject.getString("otp"));
			  }
			  
			  }
			  
			/*  JSONArray errors = json.getJSONArray("messages"); for (int i = 0;
			  i < errors.length(); i++) { JSONObject jsonOBject =
			  errors.getJSONObject(i); String msg =
			  jsonOBject.getString("message"); dto.setMessage(msg); }
			 
			data = json.getString("status");
			dto.setStatus(json.getString("status"));*/
			/*JSONArray data1 = json.getJSONArray("data");
			for (int j = 0; j < data1.length(); j++) {
				JSONObject jsonOBject = data1.getJSONObject(j);
				System.out.println(jsonOBject);
			}*/
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				dto.setMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO login(String emailId, String password) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String LOGIN_URL = "http://" + server_url + "/api/rest/user/login";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("username", emailId));
		param.add(new BasicNameValuePair("password", password));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", param);
		System.out.println("json" + json);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));

			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String timeZone = jsonOBject.getString("timezone");
					timeZone.replace("\\", "");
					dto.setTimeZone(timeZone);
					String uid = jsonOBject.getString("uid");
					dto.setuId(uid);
					dto.setFirstName(jsonOBject.getString("name"));
					dto.setEmail(jsonOBject.getString("email"));
					 dto.setCountry(jsonOBject.getString("country"));
					dto.setSid(jsonOBject.getString("sid"));
					dto.setSsid(jsonOBject.getString("ssid"));
					dto.setAccount(jsonOBject.getString("account"));
					dto.setCurrencycode(jsonOBject.getString("currency"));
					//dto.setCurrencysymbol(jsonOBject.getString("currency_symbol"));
				}

				JSONArray errors = json.getJSONArray("messages");
				for (int j = 0; j < errors.length(); j++) {
					JSONObject jsonOBject = errors.getJSONObject(j);
					String msg = jsonOBject.getString("message");
					dto.setMessage(msg);
				}

			} else {
				JSONArray errors = json.getJSONArray("messages");
				for (int i = 0; i < errors.length(); i++) {
					JSONObject jsonOBject = errors.getJSONObject(i);
					String msg = jsonOBject.getString("message");
					dto.setMessage(msg);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO forgetpassword(String emailId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String FORGETPASSWORD_URL = "http://" + server_url
				+ "/api/rest/user/password-forgot";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("email", emailId));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(FORGETPASSWORD_URL,
				"POST", param);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				dto.setMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO bookRelease(String pId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String BOOKRELEASE_URL = "http://" + server_url
				+ "/api/rest/books/release/";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("pid", pId));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(BOOKRELEASE_URL, "GET",
				param);

		try {
			JSONObject userJson = json.getJSONObject("data");
			String pid = userJson.getString("bid");

			dto.setpId(pid);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO release(String uId, String iid, String nid) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String RELEASE_URL = "http://" + server_url
				+ "/api/rest/invitations/release";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("iid", iid));
		param.add(new BasicNameValuePair("nid", nid));
		// getting product details by making HTTP request
		JSONObject json = jsonParser
				.makeHttpRequest(RELEASE_URL, "POST", param);

		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				dto.setMessage(msg);
			}
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String releaseon = jsonOBject.getString("released_on");
					dto.setReleaseOn(releaseon);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public List guest(String nid) {

		ArrayList mylist = new ArrayList();
		UserDTO dto = new UserDTO();
		String GUEST_URL = "http://" + server_url
				+ "/api/rest/invitations/guests";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("nid", nid));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(GUEST_URL, "GET", param);

		try {

			JSONArray errors = json.getJSONArray("data");
			for (int i = 0; i < errors.length(); i++) {
				Map map = new HashMap<String, String>();
				JSONObject jsonOBject = errors.getJSONObject(i);
				String name = jsonOBject.getString("name");
				String releaseon = jsonOBject.getString("released_on");
				String country = jsonOBject.getString("country");
				map.put("name", name);
				map.put("releaseon", releaseon);
				map.put("country", country);

				mylist.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mylist;
	}

	public List regionalguest(String nid, String country) {

		ArrayList mylist = new ArrayList();
		UserDTO dto = new UserDTO();
		String GUEST_URL = "http://" + server_url
				+ "/api/rest/invitations/guests";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("nid", nid));
		param.add(new BasicNameValuePair("country", country));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(GUEST_URL, "GET", param);

		try {

			JSONArray errors = json.getJSONArray("data");
			for (int i = 0; i < errors.length(); i++) {
				Map map = new HashMap<String, String>();
				JSONObject jsonOBject = errors.getJSONObject(i);
				String name = jsonOBject.getString("name");
				String releaseon = jsonOBject.getString("released_on");
				String country1 = jsonOBject.getString("country");
				map.put("name", name);
				map.put("releaseon", releaseon);
				map.put("country", country1);

				mylist.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mylist;
	}

	public UserDTO music(String title, String url) {

		UserDTO dto = new UserDTO();
		String data = null;
		try {

			dto.setTitle(title);
			dto.setUrl(url);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO orderinitaite(String uId, String amount) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String INVITATION_URL = "http://" + server_url
				+ "/api/rest/order/initiate";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("amount", amount));
		param.add(new BasicNameValuePair("device", "android"));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(INVITATION_URL, "POST",
				param);
		System.out.println("json" + json);
		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String oid = jsonOBject.getString("oid");
					dto.setOrderId(oid);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO orderupdate(String oid, String nid, String uid) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String INVITATION_URL = "http://" + server_url
				+ "/api/rest/order/update";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;
		param.add(new BasicNameValuePair("uid", uid));
		param.add(new BasicNameValuePair("oid", oid));
		param.add(new BasicNameValuePair("nid", nid));
		param.add(new BasicNameValuePair("quantity", "1"));
		param.add(new BasicNameValuePair("operation", "add"));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(INVITATION_URL, "POST",
				param);
		System.out.println("json" + json);
		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String oId = jsonOBject.getString("oid");
					dto.setOrderId(oId);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO getrsakey(String url, String orderId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String KEY_URL = url;
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("oid", orderId));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(KEY_URL, "POST", param);

		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String key = jsonOBject.getString("key");
					dto.setRsaKey(key);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO releasebook(String uId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String RELEASE_URL = "http://" + server_url + "/api/rest/books";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("status", "release"));
		param.add(new BasicNameValuePair("uid", uId));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(RELEASE_URL, "GET", param);

		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String image = jsonOBject.getString("image");
					image.replace("\\", "");
					dto.setBookImage(image);
					String releaseOn = jsonOBject.getString("released_on");
					dto.setReleaseOn(releaseOn);
					String nid = jsonOBject.getString("nid");
					dto.setnId(nid);
					String title = jsonOBject.getString("title");
					dto.setTitle(title);
					String author = jsonOBject.getString("author");
					dto.setAuthorName(author);
					String price = jsonOBject.getString("cost_price");
					dto.setPrice(price);
					String publisher = jsonOBject.getString("publisher");
					dto.setPublisher(publisher);
					String isbn = jsonOBject.getString("isbn");
					dto.setIsbn(isbn);
					String pages = jsonOBject.getString("pages");
					dto.setPages(pages);
					String format = jsonOBject.getString("format");
					dto.setFormat(format);
					String description = jsonOBject.getString("description");
					dto.setDescription(description);
					String purchase = jsonOBject.getString("purchased");
					dto.setPurchase(purchase);
					if (purchase.equalsIgnoreCase("true")) {
						dto.setOrderId(jsonOBject.getString("oid"));

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO orderHistory(String nId, String uId, String orderId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String ORDER_URL = "http://" + server_url + "/api/rest/books/download";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("nid", nId));
		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("oid", orderId));
		param.add(new BasicNameValuePair("device", "android"));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(ORDER_URL, "GET", param);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));

			if (json.getString("status").equalsIgnoreCase("success")) {

				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					dto.setFilePath(jsonOBject.getString("file_path"));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO Facebookregistration(String Name, String emailId, String sId, double latitude, double longitude,
			String location) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String REGISTRATION_URL = "http://" + server_url
				+ "/api/rest/social/login";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;
		param.add(new BasicNameValuePair("sid", sId));
		param.add(new BasicNameValuePair("name", Name));
		param.add(new BasicNameValuePair("email", emailId));
		param.add(new BasicNameValuePair("location", location));
		param.add(new BasicNameValuePair("op", "login"));
		param.add(new BasicNameValuePair("account", "facebook"));
		param.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		param.add(new BasicNameValuePair("longitude", Double
				.toString(longitude)));
		param.add(new BasicNameValuePair("device", "android"));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(REGISTRATION_URL, "POST",
				param);
		System.out.println("fbjson" + json);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					dto.setuId(jsonOBject.getString("uid"));
					dto.setFirstName(jsonOBject.getString("name"));
					dto.setEmail(jsonOBject.getString("email"));
					//String sessionobj=jsonOBject.getString("sessions");
					
					//JSONObject sessionjson=jsonOBject.getString("sid");
					// dto.setCountry(jsonOBject.getString("country"));
					dto.setSid(jsonOBject.getString("sid"));
					dto.setSsid(jsonOBject.getString("ssid"));
					dto.setAccount(jsonOBject.getString("account"));
					dto.setCurrencycode(jsonOBject.getString("currency"));
				}

				JSONArray errors = json.getJSONArray("messages");
				for (int j = 0; j < errors.length(); j++) {
					JSONObject jsonOBject = errors.getJSONObject(j);
					String msg = jsonOBject.getString("message");
					dto.setMessage(msg);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO BookRead(String uId, String nId, String oId, String sId,
			String ssId) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String REGISTRATION_URL = "http://" + server_url
				+ "/api/rest/book/read";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;
		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("nid", nId));
		param.add(new BasicNameValuePair("oid", oId));
		param.add(new BasicNameValuePair("sid", sId));
		param.add(new BasicNameValuePair("ssid", ssId));

		param.add(new BasicNameValuePair("device", "android"));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(REGISTRATION_URL, "POST",
				param);
		System.out.println("js" + json);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray link = json.getJSONArray("data");
				for (int i = 0; i < link.length(); i++) {
					JSONObject jsonOBject = link.getJSONObject(i);
					String msg = jsonOBject.getString("link");
					msg.replace("\\", "");
					msg = msg.replace("&amp;", "&");
					dto.setUrl(msg);
				}
			} else {
				JSONArray errors = json.getJSONArray("messages");
				for (int i = 0; i < errors.length(); i++) {
					JSONObject jsonOBject = errors.getJSONObject(i);
					String msg = jsonOBject.getString("message");
					dto.setMessage(msg);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO orderupdatestatus(String oid, String uid, String status,
			String gateway, String response) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String INVITATION_URL = "http://" + server_url
				+ "/api/rest/order/update-status";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;
		param.add(new BasicNameValuePair("oid", oid));
		param.add(new BasicNameValuePair("uid", uid));
		param.add(new BasicNameValuePair("status", status));
		param.add(new BasicNameValuePair("gateway", gateway));
		param.add(new BasicNameValuePair("response", response));
		param.add(new BasicNameValuePair("device", "android"));
		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(INVITATION_URL, "POST",
				param);
		System.out.println("json" + json);
		try {
			// data=json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {

			} else {
				JSONArray errors = json.getJSONArray("messages");
				for (int i = 0; i < errors.length(); i++) {
					JSONObject jsonOBject = errors.getJSONObject(i);
					String msg = jsonOBject.getString("message");
					dto.setMessage(msg);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO currentlocation(double latitude, double longitude) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String CURRENT_LOCATION_URL = "http://" + server_url
				+ "/api/rest/location/current";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;
		param.add(new BasicNameValuePair("latitude", Double.toString(latitude)));
		param.add(new BasicNameValuePair("longitude", Double
				.toString(longitude)));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(CURRENT_LOCATION_URL,
				"GET", param);
		System.out.println("js" + json);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));

			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					String timeZone = jsonOBject.getString("timezone");
					timeZone.replace("\\", "");
					dto.setTimeZone(timeZone);
					dto.setCurrencycode(jsonOBject.getString("currency_code"));
					dto.setCurrencysymbol(jsonOBject
							.getString("currency_symbol"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO verifyotp(String uId, String activationcode) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String VERIFY_OTP_URL = "http://" + server_url
				+ "/api/rest/user/activate";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("activation_code", activationcode));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(VERIFY_OTP_URL, "POST",
				param);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				dto.setMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public UserDTO resendotp(String uId, String number) {
		// TODO Auto-generated method stub
		UserDTO dto = new UserDTO();
		String RESEND_OTP_URL = "http://" + server_url
				+ "/api/rest/sms/send-otp";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("number", number));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(RESEND_OTP_URL, "POST",
				param);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));
			if (json.getString("status").equalsIgnoreCase("success")) {
				JSONArray userJson = json.getJSONArray("data");
				for (int i = 0; i < userJson.length(); i++) {
					JSONObject jsonOBject = userJson.getJSONObject(i);
					dto.setOtp(jsonOBject.getString("otp"));
					System.out.println("reotp"+jsonOBject.getString("otp"));
					dto.setPhone(jsonOBject.getString("number"));
				}
			}
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				dto.setMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
	
	public UserDTO logout(String uId, String sid,String ssid) {
		
		UserDTO dto = new UserDTO();
		String LOGOUT_URL = "http://" + server_url
				+ "/api/rest/user/logout";
		JSONParser jsonParser = new JSONParser();
		List param = new ArrayList();
		String data = null;

		param.add(new BasicNameValuePair("uid", uId));
		param.add(new BasicNameValuePair("sid", sid));
		param.add(new BasicNameValuePair("ssid", ssid));

		// getting product details by making HTTP request
		JSONObject json = jsonParser.makeHttpRequest(LOGOUT_URL, "POST",
				param);

		try {
			data = json.getString("status");
			dto.setStatus(json.getString("status"));
			JSONArray errors = json.getJSONArray("messages");
			for (int i = 0; i < errors.length(); i++) {
				JSONObject jsonOBject = errors.getJSONObject(i);
				String msg = jsonOBject.getString("message");
				System.out.println("message"+msg);
				dto.setMessage(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
}
