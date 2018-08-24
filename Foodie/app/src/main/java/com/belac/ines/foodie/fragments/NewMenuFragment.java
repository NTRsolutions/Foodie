package com.belac.ines.foodie.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.belac.ines.foodie.R;
import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.RetrofitClient;
import com.belac.ines.foodie.helper.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMenuFragment extends Fragment {

	@BindView(R.id.menu_one) EditText menuOne;
	@BindView(R.id.menu_two) EditText menuTwo;
	@BindView(R.id.menu_three) EditText menuThree;
	@BindView(R.id.price) EditText price;
	@BindView(R.id.root) LinearLayout root;

	ProgressDialog progressDialog;

	public NewMenuFragment() {
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_menu, container, false);

		ButterKnife.bind(this, view);

		progressDialog = new ProgressDialog(getActivity());

		return view;
	}

	@OnClick(R.id.add) void onClickAdd() {
		progressDialog.setMessage("Loading...");
		progressDialog.setCancelable(false);
		progressDialog.show();

		RetrofitClient.instance()
				.create(APIService.class)
				.addMenu(Integer.valueOf(SessionManager.getId(getContext())), menuOne.getText().toString(),
						menuTwo.getText().toString(), menuThree.getText().toString(),
						Integer.valueOf(price.getText().toString()))
				.enqueue(new Callback<ResponseBody>() {
					@Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
						progressDialog.dismiss();

						//hide keyboard
						if (getView() != null) {
							InputMethodManager imm =
									(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
						}

						Snackbar.make(root, "The menu is created.", Snackbar.LENGTH_SHORT).show();
					}

					@Override public void onFailure(Call<ResponseBody> call, Throwable t) {
						progressDialog.dismiss();
						Snackbar.make(root, "Something went wrong!", Snackbar.LENGTH_SHORT).show();
					}
				});
	}
}
