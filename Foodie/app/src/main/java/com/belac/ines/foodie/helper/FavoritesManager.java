package com.belac.ines.foodie.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoritesManager {

	private static final String FAVORITES_KEY = "FavoritesManager.FAVORITES_KEY";

	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static void addFavorite(Context context, int restaurantId) {

		Gson gson = new Gson();

		String favorites = getPreferences(context).getString(FAVORITES_KEY, null);
		if (favorites == null) {
			List<String> favoritesList = new ArrayList<>();
			favoritesList.add(String.valueOf(restaurantId));
			getPreferences(context).edit().putString(FAVORITES_KEY, gson.toJson(favoritesList)).apply();
		} else {
			ArrayList<String> favoritesList = new ArrayList<>(Arrays.asList(gson.fromJson(favorites, String[].class)));
			if(favoritesList.contains(String.valueOf(restaurantId))) return;
			favoritesList.add(String.valueOf(restaurantId));
			getPreferences(context).edit().putString(FAVORITES_KEY, gson.toJson(favoritesList)).apply();
		}
	}

	public static void removeFavorite(Context context, int restaurantId) {
		Gson gson = new Gson();

		String favorites = getPreferences(context).getString(FAVORITES_KEY, null);

		if (favorites != null) {
			ArrayList<String> favoritesList = new ArrayList<>(Arrays.asList(gson.fromJson(favorites, String[].class)));
			favoritesList.remove(String.valueOf(restaurantId));
			getPreferences(context).edit().putString(FAVORITES_KEY, gson.toJson(favoritesList)).apply();
		}
	}

	public static void removeAllFavorites(Context context) {
		getPreferences(context).edit().remove(FAVORITES_KEY).apply();
	}

	public static boolean isFavorite(Context context, int restaurantId) {
		Gson gson = new Gson();

		String favorites = getPreferences(context).getString(FAVORITES_KEY, null);
		if (favorites == null) {
			return false;
		} else {
			ArrayList<String> favoritesList = new ArrayList<>(Arrays.asList(gson.fromJson(favorites, String[].class)));
			return favoritesList.contains(String.valueOf(restaurantId));
		}
	}

	public static List<String> getAllFavorites(Context context) {
		Gson gson = new Gson();

		String favorites = getPreferences(context).getString(FAVORITES_KEY, null);
		return Arrays.asList(gson.fromJson(favorites, String[].class));
	}
}
