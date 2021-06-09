package com.sa.rezq.adapter.interfaces;


import com.sa.rezq.services.model.WishModel;
import com.sa.rezq.services.model.WishlistPostModel;

public interface WishListener {
    void OnDeleteInvoked(int position, WishModel wishModel);
    void OnCartInvoked(int position, WishModel wishModel);
    void OnMoveToCartInvoked(int position, WishlistPostModel wishlistPostModel);
}
