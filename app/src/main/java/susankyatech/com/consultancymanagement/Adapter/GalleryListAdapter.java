package susankyatech.com.consultancymanagement.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import susankyatech.com.consultancymanagement.API.ClientAPI;
import susankyatech.com.consultancymanagement.Activity.MainActivity;
import susankyatech.com.consultancymanagement.Application.App;
import susankyatech.com.consultancymanagement.Fragment.GalleryFragment;
import susankyatech.com.consultancymanagement.Fragment.GalleryFullScreenFragment;
import susankyatech.com.consultancymanagement.Model.Gallery;
import susankyatech.com.consultancymanagement.Model.GalleryDeleteResponse;
import susankyatech.com.consultancymanagement.Model.Login;
import susankyatech.com.consultancymanagement.R;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.GalleryViewHolder> {

    private List<Gallery> galleryList;
    private Context context;
    private ArrayList<String> stringList = new ArrayList<>();

    public GalleryListAdapter(List<Gallery> galleryList, ArrayList<String> images, Context context) {
        this.galleryList = galleryList;
        this.stringList = images;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_gallery_layout, parent, false);
        return new GalleryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, final int position) {
        final String image = galleryList.get(position).image;

        Picasso.get().load(image).into(holder.galleryImage);

        holder.galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("image", image);
                bundle.putStringArrayList("imageList", stringList);
                bundle.putInt("position", position);

                GalleryFullScreenFragment fragment = new GalleryFullScreenFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = ((MainActivity) context ).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_container, fragment).addToBackStack(null).commit();

            }
        });

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientAPI clientAPI = App.consultancyRetrofit().create(ClientAPI.class);
                clientAPI.deleteGalleryImage(galleryList.get(position).id).enqueue(new Callback<GalleryDeleteResponse>() {
                    @Override
                    public void onResponse(Call<GalleryDeleteResponse> call, Response<GalleryDeleteResponse> response) {
                        try
                        {
                            Log.d("OOPS","REACHED HERE");
                            Log.d("OOPS",response.raw().body().string());


                        }
                        catch (Exception e)
                        {
                             Log.d("OOPS",e.toString());
                        }
                        if (response.isSuccessful()){

                            if (response.body() != null){
                                Log.d("OOPS",response.body().message);
                                galleryList.remove(position);
                                notifyItemRemoved(position);
                            }
                        }else {
                            try {
                                Log.d("loginError", response.errorBody().string());
                                MDToast mdToast = MDToast.makeText(context, "Error on deleting gallery. Please try again!", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                                mdToast.show();
                            } catch (Exception e) {
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<GalleryDeleteResponse> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageView deleteImage;
        ImageView galleryImage;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            deleteImage = mView.findViewById(R.id.delete_image);
            galleryImage = mView.findViewById(R.id.all_gallery_image);
        }
    }
}
