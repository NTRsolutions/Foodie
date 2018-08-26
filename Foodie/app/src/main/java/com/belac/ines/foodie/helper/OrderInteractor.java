package com.belac.ines.foodie.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import com.belac.ines.foodie.api.APIService;
import com.belac.ines.foodie.api.Interactor;
import com.belac.ines.foodie.api.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInteractor {

	public static void orderMenu(Context context, int menuId, final Interactor interactor) {

		RetrofitClient.instance()
				.create(APIService.class)
				.order(Integer.parseInt(SessionManager.getId(context)), menuId)
				.enqueue(new Callback<ResponseBody>() {
					@Override
					public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
						interactor.onSuccess();
					}

					@Override public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
						interactor.onError();
					}
				});
	}
}
