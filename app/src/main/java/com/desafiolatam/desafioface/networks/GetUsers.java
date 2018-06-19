package com.desafiolatam.desafioface.networks;

import android.os.AsyncTask;

import com.desafiolatam.desafioface.models.Developer;
import com.desafiolatam.desafioface.networks.Users.UserInterceptor;
import com.desafiolatam.desafioface.networks.Users.Users;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class GetUsers extends AsyncTask<Map<String,String>, Integer,Integer> {

    private int adittionalPages;
    private Map<String,String> queryMap;
    private int result;
    private final Users request = new UserInterceptor().get();

    public GetUsers(int adittionalPages) {
        this.adittionalPages = adittionalPages;
    }

    @Override
    protected Integer doInBackground(Map<String, String>... params) {

        queryMap = params[0];
        if(adittionalPages <0)
        {
            while (200 == connect())
            {
                increasePage();
            }
        }else
        {
            while (adittionalPages >= 0)
            {
                adittionalPages--;
                connect();
                increasePage();
            }
        }
        return null;
    }

    private void increasePage()
    {
        int page = Integer.parseInt(queryMap.get("page"));
        page++;
        queryMap.put("page", String.valueOf(page));
    }
    private int connect()
    {
        int code = 111;
        Call<Developer[]> call = request.get(queryMap);
        try {
            Response<Developer[]> response = call.execute();
            code = response.code();
            if(200 == code && response.isSuccessful())
            {
                Developer[] developers = response.body();
                if(developers != null && developers.length > 0)
                {
                    for (Developer servDev: developers) {
                        List<Developer> localDevs = Developer.find(Developer.class, "serverId = ?", String.valueOf(servDev.getId()));

                        if(localDevs != null && localDevs.size() > 0)
                        {
                            Developer local = localDevs.get(0);
                            local.setEmail(servDev.getEmail());
                            local.setPhoto_url(servDev.getPhoto_url());
                            local.save();

                        }else
                        {
                            servDev.create();
                        }
                    }
                }else
                {
                    code = 000;
                }
            }else {

            }
        } catch (IOException e) {
            e.printStackTrace();
            code = 888;
        }
        result = code;
        return result;
    }

}
