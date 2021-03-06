package com.fa.gpsmeasure.util;

import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class CoordConvert {

	private static AsyncHttpClient client = new AsyncHttpClient();

	/**
	 * Convert GPS coordinate to Google Map coordinate.
	 * 
	 * @param longitude
	 *            GPS 经度
	 * @param latitude
	 *            GPS 纬度
	 */
	public static void parseGoogle(double longitude, double latitude,
			final NetworkListener listener) {

		StringBuilder sb = new StringBuilder(
				"http://123.125.115.154/ag/coord/convert?from=0&to=2&x=");
		sb.append(longitude);
		sb.append("&y=");
		sb.append(latitude);

		client.get(sb.toString(), new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject object) {
				// TODO Auto-generated method stub
				super.onSuccess(object);

				try {
					double lon1 = Double.parseDouble(Base64.decode(object
							.getString("x")));
					double lat1 = Double.parseDouble(Base64.decode(object
							.getString("y")));

					if ("0".equals(object.getString("error"))) {
						listener.onFinish(new GeoPoint((int) (lat1 * 1E6),
								(int) (lon1 * 1E6)));
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					listener.onError();
				}

			}

			@Override
			public void onFailure(Throwable arg0, JSONObject arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				listener.onError();
			}

		});

	}
}
