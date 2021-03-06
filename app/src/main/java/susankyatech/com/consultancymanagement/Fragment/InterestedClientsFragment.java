package susankyatech.com.consultancymanagement.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import susankyatech.com.consultancymanagement.API.ClientAPI;
import susankyatech.com.consultancymanagement.Activity.MainActivity;
import susankyatech.com.consultancymanagement.Adapters.ConsultancyListAdapter;
import susankyatech.com.consultancymanagement.Application.App;
import susankyatech.com.consultancymanagement.Decorations.HorizontalSpaceItemDecoration;
import susankyatech.com.consultancymanagement.Decorations.VerticalSpaceItemDecoration;
import susankyatech.com.consultancymanagement.Generic.Utilities;
import susankyatech.com.consultancymanagement.Model.Client;
import susankyatech.com.consultancymanagement.Model.Data;
import susankyatech.com.consultancymanagement.Model.Login;
import susankyatech.com.consultancymanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterestedClientsFragment extends Fragment {

    @BindView(R.id.matched_consultancy_list)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLayout)
    View progressLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressTV)
    TextView progressTextView;
    @BindView(R.id.card)
    CardView cardView;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.emtpyTextview)
    TextView emptyText;
    @BindView(R.id.emptyTextLayout)
    View emptyView;
    @BindView(R.id.empty_img)
    ImageView empty;

    private List<Client> clientList;
    Data data;

    public InterestedClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interested_clients, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Starred");
        init();
        return view;
    }

    private void init() {
        progressLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);

        if (Utilities.isConnectionAvailable(getActivity())) {
            emptyView.setVisibility(View.GONE);
            getInterestedClients();
        }else{
            progressLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            empty.setImageDrawable(getResources().getDrawable(R.drawable.ic_plug));
            emptyText.setText("OOPS, out of Connection");
        }

    }

    private void getInterestedClients() {
        ClientAPI clientAPI = App.consultancyRetrofit().create(ClientAPI.class);
        clientAPI.getInterestedClient("is_interested").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){

                        progressLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        cardView.setVisibility(View.VISIBLE);
                        if (response.body().data.clients.isEmpty()){
                            message.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.GONE);
                        }
                        clientList = response.body().data.clients;

                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                        recyclerView.setAdapter(new ConsultancyListAdapter(clientList, getContext()));
                        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(16));
                        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(16));
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

}
