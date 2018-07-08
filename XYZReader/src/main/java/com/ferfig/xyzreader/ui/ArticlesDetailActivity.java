package com.ferfig.xyzreader.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ferfig.xyzreader.R;
import com.ferfig.xyzreader.data.ArticleLoader;
import com.ferfig.xyzreader.data.ItemsContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ArticlesDetailActivity.class.toString();

    private static int LOADER_ID = 27;

    private Cursor mCursor;
    private long mSelectedItemId;
    private String mArticleTitle;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.detail_activity_all_content)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.app_bar)
    Toolbar mAppBar;

    @BindView(R.id.article_image)
    ProperSizeImageView mArticleImage;

    @BindView(R.id.article_subtitle)
    TextView mArticleSubtitle;

    @BindView(R.id.article_body)
    TextView mArticleBody;

    @BindView (R.id.fabActionShare)
    FloatingActionButton faButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_articles_detail);

        ButterKnife.bind(this);

        mAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(ArticlesDetailActivity.this)
                        .setType("text/plain")
                        .setText(String.format(getString(R.string.share_msg), mArticleTitle))
                        .getIntent(), getString(R.string.action_share)));
            }
        });

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getData() != null) {
                mSelectedItemId = ItemsContract.Items.getItemId(getIntent().getData());

                getSupportLoaderManager().initLoader(LOADER_ID, null, this);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(this, mSelectedItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;

        // Select the start ID
        if (mCursor.moveToFirst()) {
            Picasso.get().load(mCursor.getString(ArticleLoader.Query.PHOTO_URL)).into(mArticleImage);
            mArticleTitle = mCursor.getString(ArticleLoader.Query.TITLE);
            mCollapsingToolbarLayout.setTitle(mArticleTitle);
            mArticleSubtitle.setText(mCursor.getString(ArticleLoader.Query.AUTHOR));
            mArticleBody.setText(Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY).replaceAll("(\r\n|\n)", "<br />")));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getSupportLoaderManager().destroyLoader(LOADER_ID);
    }
}
