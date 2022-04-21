package de.mopsdom.webviewbg;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.webkit.WebView;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;


public class Webviewbg extends CordovaPlugin {

    private final String pluginName = "cordova-plugin-webviewbg";

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {

		if (action.equals("isDarkMode")){
			int nightModeFlags =
				this.cordova.getActivity().getResources().getConfiguration().uiMode &
				Configuration.UI_MODE_NIGHT_MASK;
			PluginResult presult;
			switch (nightModeFlags) {
				case Configuration.UI_MODE_NIGHT_YES:
					presult = new PluginResult(PluginResult.Status.OK, "true");
                    callbackContext.sendPluginResult(presult);
					break;

				case Configuration.UI_MODE_NIGHT_NO:
					presult = new PluginResult(PluginResult.Status.OK, "false");
                    callbackContext.sendPluginResult(presult);
					break;

				case Configuration.UI_MODE_NIGHT_UNDEFINED:
					presult = new PluginResult(PluginResult.Status.OK, "undefined");
                    callbackContext.sendPluginResult(presult);
					break;
			}
			
			
			return true;
		}
		else
        if (action.equals("setBG")||action.equals("setTransparent")) {
            final WebView webView = (WebView) this.webView.getEngine().getView();
            try
            {
                Handler mainHandler = new Handler(cordova.getActivity().getMainLooper());
                final Looper myLooper = Looper.myLooper();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

						try
						{
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
									webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
						catch (Exception e)
						{
							new Handler(myLooper).post(new Runnable() {
								@Override
								public void run() {
									 callbackContext.error(e.getMessage());
								}
							});
						}
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
