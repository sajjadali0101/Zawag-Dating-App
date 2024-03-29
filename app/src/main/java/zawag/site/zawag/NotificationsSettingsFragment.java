package com.raccoonsquare.dating;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raccoonsquare.dating.app.App;
import com.raccoonsquare.dating.constants.Constants;
import com.raccoonsquare.dating.util.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationsSettingsFragment extends PreferenceFragmentCompat implements Constants {

    private CheckBoxPreference mAllowLikesGCM, mAllowFollowersGCM, mAllowMessagesGCM, mAllowGiftsGCM, mAllowMatchesGCM;

    private ProgressDialog pDialog;

    int mAllowLikes, mAllowFollowers, mAllowMessages, mAllowGifts, mAllowMatches;

    private Boolean loading = false;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.notifications_settings, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        initpDialog();

        mAllowMatchesGCM = (CheckBoxPreference) getPreferenceManager().findPreference("allowMatchesGCM");

        mAllowMatchesGCM.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue instanceof Boolean) {

                    Boolean value = (Boolean) newValue;

                    if (value) {

                        mAllowMatches = 1;

                    } else {

                        mAllowMatches = 0;
                    }

                    if (App.getInstance().isConnected()) {

                        setAllowMatches();

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        });

        mAllowLikesGCM = (CheckBoxPreference) getPreferenceManager().findPreference("allowLikesGCM");

        mAllowLikesGCM.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue instanceof Boolean) {

                    Boolean value = (Boolean) newValue;

                    if (value) {

                        mAllowLikes = 1;

                    } else {

                        mAllowLikes = 0;
                    }

                    if (App.getInstance().isConnected()) {

                        setAllowLikes();

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        });

        mAllowFollowersGCM = (CheckBoxPreference) getPreferenceManager().findPreference("allowFollowersGCM");

        mAllowFollowersGCM.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue instanceof Boolean) {

                    Boolean value = (Boolean) newValue;

                    if (value) {

                        mAllowFollowers = 1;

                    } else {

                        mAllowFollowers = 0;
                    }

                    if (App.getInstance().isConnected()) {

                        setAllowFollowers();

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        });

        mAllowMessagesGCM = (CheckBoxPreference) getPreferenceManager().findPreference("allowMessagesGCM");

        mAllowMessagesGCM.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue instanceof Boolean) {

                    Boolean value = (Boolean) newValue;

                    if (value) {

                        mAllowMessages = 1;

                    } else {

                        mAllowMessages = 0;
                    }

                    if (App.getInstance().isConnected()) {

                        setAllowMessages();

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        });

        mAllowGiftsGCM = (CheckBoxPreference) getPreferenceManager().findPreference("allowGiftsGCM");

        mAllowGiftsGCM.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                if (newValue instanceof Boolean) {

                    Boolean value = (Boolean) newValue;

                    if (value) {

                        mAllowGifts = 1;

                    } else {

                        mAllowGifts = 0;
                    }

                    if (App.getInstance().isConnected()) {

                        setAllowGifts();

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), getText(R.string.msg_network_error), Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }
        });

        checkAllowMatches(App.getInstance().getAllowMatchesGCM());
        checkAllowLikes(App.getInstance().getAllowLikesGCM());
        checkAllowFollowers(App.getInstance().getAllowFollowersGCM());
        checkAllowMessages(App.getInstance().getAllowMessagesGCM());
        checkAllowGifts(App.getInstance().getAllowGiftsGCM());
    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            loading = savedInstanceState.getBoolean("loading");

        } else {

            loading = false;
        }

        if (loading) {

            showpDialog();
        }
    }

    public void onDestroyView() {

        super.onDestroyView();

        hidepDialog();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putBoolean("loading", loading);
    }

    public void checkAllowMatches(int value) {

        if (value == 1) {

            mAllowMatchesGCM.setChecked(true);
            mAllowMatches = 1;

        } else {

            mAllowMatchesGCM.setChecked(false);
            mAllowMatches = 0;
        }
    }

    public void checkAllowLikes(int value) {

        if (value == 1) {

            mAllowLikesGCM.setChecked(true);
            mAllowLikes = 1;

        } else {

            mAllowLikesGCM.setChecked(false);
            mAllowLikes = 0;
        }
    }

    public void checkAllowFollowers(int value) {

        if (value == 1) {

            mAllowFollowersGCM.setChecked(true);
            mAllowFollowers = 1;

        } else {

            mAllowFollowersGCM.setChecked(false);
            mAllowFollowers = 0;
        }
    }

    public void checkAllowMessages(int value) {

        if (value == 1) {

            mAllowMessagesGCM.setChecked(true);
            mAllowMessages = 1;

        } else {

            mAllowMessagesGCM.setChecked(false);
            mAllowMessages = 0;
        }
    }

    public void checkAllowGifts(int value) {

        if (value == 1) {

            mAllowGiftsGCM.setChecked(true);
            mAllowGifts = 1;

        } else {

            mAllowGiftsGCM.setChecked(false);
            mAllowGifts = 0;
        }
    }

    public void setAllowMatches() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_SETTINGS_MATCHES_GCM, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (!response.getBoolean("error")) {

                                App.getInstance().setAllowMatchesGCM(response.getInt("allowMatchesGCM"));

                                checkAllowMatches(App.getInstance().getAllowMatchesGCM());
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            loading = false;

                            hidepDialog();

                            App.getInstance().saveData();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading = false;

                hidepDialog();

                Toast.makeText(getActivity().getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("clientId", CLIENT_ID);
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("allowMatchesGCM", Integer.toString(mAllowMatches));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void setAllowLikes() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_SETTINGS_LIKES_GCM, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (!response.getBoolean("error")) {

                                App.getInstance().setAllowLikesGCM(response.getInt("allowLikesGCM"));

                                checkAllowLikes(App.getInstance().getAllowLikesGCM());
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            loading = false;

                            hidepDialog();

                            App.getInstance().saveData();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading = false;

                hidepDialog();

                Toast.makeText(getActivity().getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("clientId", CLIENT_ID);
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("allowLikesGCM", Integer.toString(mAllowLikes));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void setAllowFollowers() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_SETTINGS_FOLLOWERS_GCM, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (!response.getBoolean("error")) {

                                App.getInstance().setAllowFollowersGCM(response.getInt("allowFollowersGCM"));

                                checkAllowFollowers(App.getInstance().getAllowFollowersGCM());
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            loading = false;

                            hidepDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading = false;

                hidepDialog();

                Toast.makeText(getActivity().getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("clientId", CLIENT_ID);
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("allowFollowersGCM", Integer.toString(mAllowFollowers));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void setAllowMessages() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_SETTINGS_MESSAGES_GCM, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (!response.getBoolean("error")) {

                                App.getInstance().setAllowMessagesGCM(response.getInt("allowMessagesGCM"));

                                checkAllowMessages(App.getInstance().getAllowMessagesGCM());

                                App.getInstance().saveData();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            loading = false;

                            hidepDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading = false;

                hidepDialog();

                Toast.makeText(getActivity().getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("clientId", CLIENT_ID);
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("allowMessagesGCM", Integer.toString(mAllowMessages));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    public void setAllowGifts() {

        loading = true;

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_SETTINGS_GIFTS_GCM, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (!response.getBoolean("error")) {

                                App.getInstance().setAllowGiftsGCM(response.getInt("allowGiftsGCM"));

                                checkAllowGifts(App.getInstance().getAllowGiftsGCM());
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();

                        } finally {

                            loading = false;

                            hidepDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading = false;

                hidepDialog();

                Toast.makeText(getActivity().getApplicationContext(), getText(R.string.error_data_loading), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("clientId", CLIENT_ID);
                params.put("accountId", Long.toString(App.getInstance().getId()));
                params.put("accessToken", App.getInstance().getAccessToken());
                params.put("allowGiftsGCM", Integer.toString(mAllowGifts));

                return params;
            }
        };

        App.getInstance().addToRequestQueue(jsonReq);
    }

    protected void initpDialog() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(false);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing())
            pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}