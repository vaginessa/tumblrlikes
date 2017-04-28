package nl.acidcats.tumblrlikes.ui.widgets;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.acidcats.tumblrlikes.R;
import nl.acidcats.tumblrlikes.data.repo.photo.PhotoRepo;

/**
 * Created by stephan on 28/04/2017.
 */

public class PhotoActionDialog extends FrameLayout {
    public static final String TAG = PhotoActionDialog.class.getSimpleName();

    private static final String KEY_PHOTO_URL = "key_photoUrl";

    @BindView(R.id.btn_favorite)
    TextView _favoriteButton;
    @BindView(R.id.btn_hide)
    TextView _hideButton;
    @BindView(R.id.btn_like)
    TextView _likeButton;
    @BindView(R.id.btn_unlike)
    TextView _unlikeButton;
    @BindView(R.id.background)
    View _background;

    private String _photoUrl;
    private Unbinder _unbinder;
    private PhotoRepo _photoRepo;

    public PhotoActionDialog(Context context) {
        super(context);

        init();
    }

    public PhotoActionDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public PhotoActionDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setPhotoRepo(PhotoRepo photoRepo) {
        _photoRepo = photoRepo;
    }

    public void setPhotoUrl(String photoUrl) {
        _photoUrl = photoUrl;
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_photo_menu, this, true);
        _unbinder = ButterKnife.bind(this, view);

        _favoriteButton.setOnClickListener(this::onFavoriteButtonClick);
        _likeButton.setOnClickListener(this::onLikeButtonClick);
        _unlikeButton.setOnClickListener(this::onUnlikeButtonClick);
        _hideButton.setOnClickListener(this::onHideButtonClick);
        _background.setOnClickListener(this::onBackgroundClick);

        hide();
    }

    private void onBackgroundClick(View view) {
        Log.d(TAG, "onBackgroundClick: ");

        hide();
    }

    private void onHideButtonClick(View view) {
        Log.d(TAG, "onHideButtonClick: ");

        hide();
    }

    private void onUnlikeButtonClick(View view) {
        Log.d(TAG, "onUnlikeButtonClick: ");

        hide();
    }

    private void onLikeButtonClick(View view) {
        Log.d(TAG, "onLikeButtonClick: ");

        hide();
    }

    private void onFavoriteButtonClick(View view) {
        Log.d(TAG, "onFavoriteButtonClick: ");

        hide();
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void onDestroy() {
        _favoriteButton.setOnClickListener(null);
        _likeButton.setOnClickListener(null);
        _unlikeButton.setOnClickListener(null);
        _hideButton.setOnClickListener(null);
        _background.setOnClickListener(null);

        _unbinder.unbind();
    }
}