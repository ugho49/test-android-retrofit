package fr.ughostephan.myappretrofit.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.ughostephan.myappretrofit.R;
import fr.ughostephan.myappretrofit.adapter.PostListViewAdapter;
import fr.ughostephan.myappretrofit.model.Comment;
import fr.ughostephan.myappretrofit.model.Post;
import fr.ughostephan.myappretrofit.service.PostService;
import fr.ughostephan.myappretrofit.util.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview_products)
    ListView listviewProducts;

    private Call<List<Post>> callPosts;
    private Call<List<Comment>> callComments;
    private PostService postService;
    private PostListViewAdapter postAdapter;
    private Unbinder unbinder;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);

        // specify an adapter (see also next example)
        postAdapter = new PostListViewAdapter(getContext());
        listviewProducts.setAdapter(postAdapter);

        // Get Post Service
        postService = RetrofitBuilder.createService(PostService.class);
        getPosts();

        // Set listView Click item Listener
        listviewProducts.setOnItemClickListener(this);

        return view;
    }

    private void getPosts() {
        callPosts = postService.getAllPosts();
        callPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postAdapter.setPostsList(response.body());
                    postAdapter.notifyDataSetChanged();
                } else {
                    Log.w(getTag(), "response in error : " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.w(getTag(), "onFailure: " + t.getMessage());
            }
        });
    }

    private void getCommentsForPost(Post post) {
        callComments = postService.getAllCommentByPostId(post.getId());
        callComments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Load comments for clicked post on success", Toast.LENGTH_LONG).show();
                } else {
                    Log.w(getTag(), "response in error : " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.w(getTag(), "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Post postClicked = postAdapter.getItem(position);
        Toast.makeText(getContext(), "Post " + postClicked.getId() + " Clicked", Toast.LENGTH_LONG).show();
        getCommentsForPost(postClicked);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (callPosts != null) {
            callPosts.cancel();
            callPosts = null;
        }

        unbinder.unbind();
    }
}
