package com.example.newsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.newsapp.Adapters.StackAdapter;
import com.example.newsapp.Models.Article;
import com.example.newsapp.Models.News;
import com.example.newsapp.R;
import com.example.newsapp.Retrofit.ApiClient;
import com.example.newsapp.Retrofit.ApiInterface;
import com.example.newsapp.Utils.AppConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStackActivity extends AppCompatActivity implements SwipeStack.SwipeStackListener {

    @BindView(R.id.swipeStack) SwipeStack swipeStack;
    @BindView(R.id.relativeArticleNotFound) RelativeLayout relativeLayoutNotFound;

    ApiInterface apiInterface;
    List<Article> arrayListArticles;
    StackAdapter stackAdapter;
    String sourceId, webHotUrl;

    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stack);


        MobileAds.initialize(this,
                "ca-app-pub-9028512770259391~2055428259");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ButterKnife.bind(this);

        init();

        getDataFromPreviousActivity();

    }

    private void getDataFromPreviousActivity()
    {
        sourceId = getIntent().getStringExtra("sourceId");

        if (!sourceId.isEmpty())
        {
            loadNews(sourceId);
        }
    }

    private void loadNews(String sourceId)
    {
            apiInterface.getNewsArticles(ApiClient.getApiUrl(sourceId, AppConstants.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            arrayListArticles.clear();

                            webHotUrl = response.body().getArticles().get(0).getUrl();
                            arrayListArticles.addAll(response.body().getArticles());

                            stackAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            Toast.makeText(ListStackActivity.this, "Error:" +t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
    }

    public void init()
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        arrayListArticles = new ArrayList<>();
        stackAdapter = new StackAdapter(arrayListArticles, this);
        swipeStack.setAdapter(stackAdapter);
        swipeStack.setListener(this);
    }



    @Override
    public void onViewSwipedToLeft(int position) {

    }

    @Override
    public void onViewSwipedToRight(int position) {

    }

    @Override
    public void onStackEmpty() {
        swipeStack.setVisibility(View.GONE);
        relativeLayoutNotFound.setVisibility(View.VISIBLE);
    }
}
