package fr.ughostephan.myappretrofit.service;

import java.util.List;

import fr.ughostephan.myappretrofit.model.Comment;
import fr.ughostephan.myappretrofit.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ughostephan on 10/01/2018.
 */
public interface PostService {

    @GET("posts")
    Call<List<Post>> getAllPosts();

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getAllCommentByPostId(@Path("postId") int postId);
}
