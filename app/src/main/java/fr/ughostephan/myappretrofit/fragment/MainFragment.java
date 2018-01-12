package fr.ughostephan.myappretrofit.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.ughostephan.myappretrofit.R;
import fr.ughostephan.myappretrofit.adapter.OnItemClickListener;
import fr.ughostephan.myappretrofit.adapter.PostAdapter;
import fr.ughostephan.myappretrofit.model.Comment;
import fr.ughostephan.myappretrofit.model.Post;
import fr.ughostephan.myappretrofit.service.PostService;
import fr.ughostephan.myappretrofit.util.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements OnItemClickListener {

    @BindView(R.id.recyclerview_products)
    RecyclerView recyclerViewProducts;

    private PostAdapter postAdapter;
    private Call<List<Post>> callPosts;
    private Call<List<Comment>> callComments;
    private PostService postService;
    private Unbinder unbinder;

    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewProducts.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewProducts.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        postAdapter = new PostAdapter(getContext());
        postAdapter.setOnItemClickListener(this);
        recyclerViewProducts.setAdapter(postAdapter);

        // Get Post Service
        postService = RetrofitBuilder.createService(PostService.class);
        getPosts();

        return view;
    }

    @Override
    public void onItemClick(Object item) {
        Post postClicked = (Post) item;
        Toast.makeText(getContext(), "Post " + postClicked.getId() + " Clicked", Toast.LENGTH_LONG).show();
        getCommentsForPost(postClicked);
    }

    private void getPosts() {
        callPosts = postService.getAllPosts();
        callPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postAdapter.setPosts(response.body());
                    postAdapter.notifyDataSetChanged();
                } else {
                    Log.w(getTag(), "response in error : " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.w(getTag(), "onFailure: " + t.getMessage() );
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
                Log.w(getTag(), "onFailure: " + t.getMessage() );
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(callPosts != null){
            callPosts.cancel();
            callPosts = null;
        }

        unbinder.unbind();
    }
}
