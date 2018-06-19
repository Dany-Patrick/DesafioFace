package com.desafiolatam.desafioface.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.data.DevelopersQueries;
import com.desafiolatam.desafioface.models.Developer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DevelopersAdapter extends RecyclerView.Adapter<DevelopersAdapter.ViewHolder> {

    private List<Developer> developers = new DevelopersQueries().all();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_developer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Developer developer = developers.get(position);
        final Context context = holder.itemView.getContext();
        Picasso.get().load(developer.getPhoto_url()).centerCrop().fit().into(holder.photo);
        holder.textView.setText(developer.getName());

        holder.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = developers.get(holder.getAdapterPosition()).getTwitter();
                webIntent(url, context);
            }
        });

        holder.github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = developers.get(holder.getAdapterPosition()).getGithub();
                webIntent(url, context);
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("*/*");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{developers.get(holder.getAdapterPosition()).getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contacto DesafioFace");
                intent.putExtra(Intent.EXTRA_TEXT, "Hola, te encontré en la app DesafioFace, hagamos un proyecto");
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });

        holder.poke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO trigger push notification
            }
        });
    }

    private void webIntent(String url, Context context) {
        if (url != null && url.trim().length() > 0) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public void update() {
        developers.clear();
        developers.addAll(new DevelopersQueries().all());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public void find(String name) {
        developers.clear();
        developers.addAll(new DevelopersQueries().findByName(name));
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo, twitter, github, email, poke;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            photo =  itemView.findViewById(R.id.developerIv);
            twitter =  itemView.findViewById(R.id.twitterBtn);
            github =  itemView.findViewById(R.id.githubBtn);
            email =  itemView.findViewById(R.id.emailBtn);
            poke =  itemView.findViewById(R.id.pokeBtn);
            textView =  itemView.findViewById(R.id.developerTv);
        }

    }

}