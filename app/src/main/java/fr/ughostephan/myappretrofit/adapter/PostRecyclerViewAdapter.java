package fr.ughostephan.myappretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ughostephan.myappretrofit.R;
import fr.ughostephan.myappretrofit.model.Post;
import fr.ughostephan.myappretrofit.util.LoremPicsum;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by ughostephan on 10/01/2018.
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private List<Post> postsList = new ArrayList<>();
    private OnItemRecyclerClickListener onItemClickListener;
    private Context context;

    public PostRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(context, postsList.get(position), onItemClickListener);
    }

    public void setOnItemClickListener(OnItemRecyclerClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosts(List<Post> posts) {
        this.postsList = posts;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_content)
        TextView itemContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Context context, final Post post, final OnItemRecyclerClickListener listener) {

            itemTitle.setText(post.getTitle());
            itemContent.setText(post.getBody());

            Picasso.with(context)
                    .load(LoremPicsum.getRandomImageUrl())
//                    .transform(new CropCircleTransformation())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(itemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(post);
                }
            });
        }
    }

}
