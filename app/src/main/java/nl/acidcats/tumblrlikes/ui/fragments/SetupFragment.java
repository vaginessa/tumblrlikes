package nl.acidcats.tumblrlikes.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import nl.acidcats.tumblrlikes.BuildConfig;
import nl.acidcats.tumblrlikes.LikesApplication;
import nl.acidcats.tumblrlikes.R;
import nl.acidcats.tumblrlikes.data.constants.Broadcasts;
import nl.acidcats.tumblrlikes.data.repo.app.AppRepo;
import nl.acidcats.tumblrlikes.util.TextWatcherAdapter;

/**
 * Created by stephan on 29/04/2017.
 */

public class SetupFragment extends Fragment {
    private static final String TAG = SetupFragment.class.getSimpleName();

    private static final String BLOG_EXT = ".tumblr.com";

    @Inject
    AppRepo _appRepo;

    @BindView(R.id.input_tumblr_blog)
    EditText _tumblrBlogInput;
    @BindView(R.id.btn_ok)
    Button _okButton;
    @BindView(R.id.blog_ext_txt)
    TextView _blogExtensionText;

    private Unbinder _unbinder;
    private TextWatcherAdapter _textWatcher;

    public static SetupFragment newInstance() {
        return new SetupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((LikesApplication) getActivity().getApplication()).getMyComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        _unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        _textWatcher = new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SetupFragment.this.onTextChanged(s.toString());
            }
        };
        _tumblrBlogInput.addTextChangedListener(_textWatcher);

        String tumblrBlog = _appRepo.getTumblrBlog();
        if (tumblrBlog == null && BuildConfig.DEBUG && !TextUtils.isEmpty(BuildConfig.BLOG)) {
            tumblrBlog = BuildConfig.BLOG;
        }

        if (tumblrBlog != null) {
            if (tumblrBlog.endsWith(BLOG_EXT)) {
                tumblrBlog = tumblrBlog.replace(BLOG_EXT, "");
            }

            _tumblrBlogInput.setText(tumblrBlog);
            _tumblrBlogInput.setSelection(tumblrBlog.length());

            _okButton.setEnabled(true);
        } else {
            _okButton.setEnabled(false);
        }

        _blogExtensionText.setText(BLOG_EXT);

        _okButton.setOnClickListener(this::onOkButtonClick);
    }

    private void onOkButtonClick(View view) {
        _appRepo.setTumblrBlog(_tumblrBlogInput.getText().toString() + BLOG_EXT);

        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(Broadcasts.SETUP_COMPLETE));
    }

    private void onTextChanged(String blog) {
        _okButton.setEnabled(!"".equals(blog));
    }

    @Override
    public void onDestroy() {
        _tumblrBlogInput.removeTextChangedListener(_textWatcher);

        _unbinder.unbind();

        super.onDestroy();
    }
}
