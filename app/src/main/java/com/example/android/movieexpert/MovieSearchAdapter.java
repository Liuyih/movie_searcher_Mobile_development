package com.example.android.movieexpert;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.android.movieexpert.data.MovieRepo;
import com.example.android.movieexpert.utils.MovieUtils;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.SearchResultViewHolder> {
    private List<MovieRepo> mRepos;
    private static final String TAG = MovieSearchAdapter.class.getSimpleName();
    private OnSearchItemClickListener mSeachItemClickListener;

    interface OnSearchItemClickListener {
        void onSearchItemClick(MovieRepo repo);
    }

    MovieSearchAdapter(OnSearchItemClickListener searchItemClickListener) {
        mSeachItemClickListener = searchItemClickListener;
    }

    public void updateSearchResults(List<MovieRepo> repos) {
        mRepos = repos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mRepos != null) {
            return mRepos.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.bind(mRepos.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mSearchResultTV;
        private ImageView mImageView;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            mSearchResultTV = itemView.findViewById(R.id.tv_search_result);
            mImageView = itemView.findViewById(R.id.iv_img_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MovieRepo searchResult = mRepos.get(getAdapterPosition());
                    mSeachItemClickListener.onSearchItemClick(searchResult);
                }
            });
        }
        public void bind(MovieRepo movie) {
            mSearchResultTV.setText(movie.title);
            //mSearchResultNumTV.setText(count+". ");
            Log.d(TAG, "Poster_path is: " + movie.poster_path);
            if(movie.poster_path == null){
                Log.d(TAG, "Poster_path is: " + null);
                Glide.with(mImageView.getContext()).load("https://www.mememaker.net/api/bucket?path=static/img/memes/full/2017/Apr/19/16/no-worries-its-all-good.jpg").transition(DrawableTransitionOptions.withCrossFade()).into(mImageView);
            }
            else {
                String iconURL = MovieUtils.buildMoviePosterURL(300, movie.poster_path);
                Glide.with(mImageView.getContext()).load(iconURL).transition(DrawableTransitionOptions.withCrossFade()).into(mImageView);
            }
        }
    }
}