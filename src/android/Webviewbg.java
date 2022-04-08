package de.mopsdom.webviewbg;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;


public class Webviewbg extends CordovaPlugin {

    private final String pluginName = "cordova-plugin-webviewbg";

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {

        if (action.equals("setBG")||action.equals("setTransparent")) {
            final WebView webView = (WebView) this.webView.getEngine().getView();
            try
            {
                Handler mainHandler = new Handler(cordova.getActivity().getMainLooper());
                final Looper myLooper = Looper.myLooper();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (action.equals("setBG")) {
                            JSONObject obj = data.getJSONObject(0);
							String value = obj.getString("value");
							if (!value.startsWith("#"))
							{
								value+="#"+value;
							}
							webView.setBackgroundColor(Color.parseColor(value));
                        }
                        else if (action.equals("setTransparent")) {
							JSONObject obj = data.getJSONObject(0);
							if (obj.getBoolean("value"))
							{
								webView.setBackgroundColor(Color.TRANSPARENT);
							}
                            else 
						    {
							   webView.setBackgroundColor(Color.WHITE);
						    }
                        }

                        new Handler(myLooper).post(new Runnable() {
                            @Override
                            public void run() {
                                callbackContext.success();
                            }
                        });
                    }
                });

            } catch (Throwable e) {
                callbackContext.error(e.getMessage());
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
