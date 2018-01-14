package fr.ughostephan.myappretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class PostListViewAdapter extends BaseAdapter {

    private List<Post> postsList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public PostListViewAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPostsList(List<Post> postsList) {
        this.postsList = postsList;
    }

    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public Post getItem(int position) {
        return postsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.item_listview_post, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        holder.bind(context, postsList.get(position));

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_content)
        TextView itemContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void bind(final Context context, final Post post) {

            itemTitle.setText(post.getTitle());
            itemContent.setText(post.getBody());

            Picasso.with(context)
                    .load(LoremPicsum.getRandomImageUrl())
//                    .transform(new CropCircleTransformation())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(itemImage);
        }
    }
}
